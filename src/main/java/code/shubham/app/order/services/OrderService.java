package code.shubham.app.order.services;

import code.shubham.commons.contexts.CorrelationIDContext;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.models.Event;
import code.shubham.commons.publishers.Publisher;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.app.constants.EventName;
import code.shubham.app.constants.EventType;
import code.shubham.app.order.dao.entities.Order;
import code.shubham.app.order.dao.entities.OrderItem;
import code.shubham.app.order.dao.entities.OrderStatus;
import code.shubham.app.order.dao.repositories.OrderRepository;
import code.shubham.app.ordermodels.CreateOrderCommand;
import code.shubham.app.ordermodels.OrderDTO;
import code.shubham.app.ordermodels.OrderEventData;
import code.shubham.app.ordermodels.OrderItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class OrderService {

	@Value("${order.status.sequence}")
	private List<OrderStatus> statusSequence;

	@Value("${order.kafka.topic.name}")
	private String topicName;

	private final OrderRepository repository;

	private final Publisher publisher;

	private final OrderItemService orderItemService;

	@Autowired
	public OrderService(final OrderRepository repository, final Publisher publisher,
			final OrderItemService orderItemService) {
		this.repository = repository;
		this.publisher = publisher;
		this.orderItemService = orderItemService;
	}

	public List<Order> fetchAllByUserId(final String userId) {
		return this.repository.findAllByUserId(userId);
	}

	@Transactional(rollbackFor = Exception.class)
	public Order create(final CreateOrderCommand command) {
		final Order order = Order.builder()
			.userId(command.getUserId())
			.customerId(command.getCustomerId())
			.customerType(command.getCustomerType())
			.status(OrderStatus.CREATED)
			.uniqueReferenceId(command.getClientReferenceId())
			.build();
		final Order persisted = this.save(order);
		final List<OrderItem> orderItems = this.orderItemService.save(order.getId(), command.getProducts());
		this.publishEvent(persisted, orderItems, EventName.OrderCreated);
		return persisted;
	}

	@Transactional(rollbackFor = Exception.class)
	public Order updateStatus(final String orderId, final OrderStatus completedStatus) {
		final Order order = this.repository.findById(orderId)
			.orElseThrow(() -> new InvalidRequestException("orderId", "No order found for orderId: %s", orderId));

		if (OrderStatus.COMPLETED.equals(order.getStatus()))
			throw new InvalidRequestException("orderId", "order completed for orderId: %s", orderId);

		if (!completedStatus.equals(order.getStatus()))
			throw new InvalidRequestException("completedStatus", "invalid status in request: %s",
					completedStatus.name());

		return this.setNextStatus(order);
	}

	public Order setNextStatus(final Order order) {
		order.setStatus(Utils.getNextInSequence(this.statusSequence, order.getStatus()));
		Order updated = this.save(order);
		this.publishEvent(updated, List.of(), EventName.OrderStatusUpdated);
		return updated;
	}

	private Order save(final Order order) {
		log.info("[STARTED] persisting order with id: {}", order.getId());
		final Order updated = this.repository.save(order);
		log.info("[COMPLETED] persisted order with id: {}", order.getId());
		return updated;
	}

	private void publishEvent(final Order order, final List<OrderItem> products, final EventName eventName) {
		log.info("[PUBLISHING] [{}] event for order Id: {}", eventName.name(), order.getId());
		this.publisher.send(topicName,
				Event.builder()
					.eventName(eventName.name())
					.eventType(EventType.ORDER.name())
					.data(JsonUtils.get(OrderEventData.builder()
						.order(OrderDTO.builder()
							.orderId(order.getId())
							.status(order.getStatus().name())
							.customerId(order.getCustomerId())
							.customerType(order.getCustomerType())
							.userId(order.getUserId())
							.uniqueReferenceId(order.getUniqueReferenceId())
							.build())
						.products(products.stream()
							.map(product -> OrderItemDTO.builder()
								.productId(product.getProductId())
								.quantity(product.getQuantity())
								.status(product.getStatus().name())
								.clientReferenceId(product.getUniqueReferenceId())
								.build())
							.toList())
						.build()))
					.createdAt(new Date())
					.userId(order.getUserId())
					.uniqueReferenceId(order.getUniqueReferenceId())
					.correlationId(CorrelationIDContext.get())
					.build());
		log.info("[PUBLISHED] [{}] event for order Id: {}", eventName.name(), order.getId());
	}

	public Optional<Order> fetchByUserIdAndOrderOrderId(final String userId, final String orderId) {
		return this.repository.findByIdAndUserId(orderId, userId);
	}

	public Optional<Order> fetchByOrderId(final String orderId) {
		return this.repository.findById(orderId);
	}

}
