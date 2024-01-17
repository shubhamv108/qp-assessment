package code.shubham.app.order.services;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.Utils;
import code.shubham.app.order.dao.entities.OrderItem;
import code.shubham.app.order.dao.entities.OrderItemStatus;
import code.shubham.app.order.dao.repositories.OrderItemRepository;
import code.shubham.app.ordermodels.OrderItemDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderItemService {

	@Value("${order.item.status.sequence}")
	private List<OrderItemStatus> statusSequence;

	private final OrderItemRepository repository;

	@Autowired
	public OrderItemService(final OrderItemRepository repository) {
		this.repository = repository;
	}

	public List<OrderItem> save(final String orderId, final List<OrderItemDTO> products) {
		final List<OrderItem> orderItems = products.stream()
			.map(productOrder -> OrderItem.builder()
				.orderId(orderId)
				.quantity(productOrder.getQuantity())
				.status(OrderItemStatus.CREATED)
				.productId(productOrder.getProductId())
				.uniqueReferenceId(productOrder.getClientReferenceId())
				.build())
			.toList();

		log.info("[STARTED] persisting products for order id: {}", orderId);
		final var persisted = this.repository.saveAll(orderItems);
		log.info("[COMPLETED] persisted products for order id: {}", orderId);
		return persisted;
	}

	public OrderItem updateStatus(final OrderItemStatus completedStatus, final String uniqueReferenceId) {
		final OrderItem orderItem = this.repository.findByUniqueReferenceId(uniqueReferenceId)
			.orElseThrow(() -> new InvalidRequestException("uniqueReferenceId",
					"no product with uniqueReferenceId %s found for order", uniqueReferenceId));
		if (OrderItemStatus.COMPLETED.name().equals(orderItem.getStatus().name()))
			throw new InvalidRequestException("uniqueReferenceId", "order for product already completed");
		if (!completedStatus.name().equals(orderItem.getStatus().name()))
			throw new InvalidRequestException("completedStatus", "invalid completedStatus: %s", completedStatus.name());
		orderItem.setStatus(Utils.getNextInSequence(this.statusSequence, completedStatus));
		return this.repository.save(orderItem);
	}

	public List<OrderItem> fetchAllByOrderId(final String orderId) {
		return this.repository.findAllByOrderId(orderId);
	}

}
