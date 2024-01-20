package code.shubham.app.order.services;

import code.shubham.app.inventorycommons.IInventoryService;
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
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderItemService {

	@Value("${order.item.status.sequence}")
	private List<OrderItemStatus> statusSequence;

	private final OrderItemRepository repository;

	private final IInventoryService inventoryService;

	@Autowired
	public OrderItemService(final OrderItemRepository repository, final IInventoryService inventoryService) {
		this.repository = repository;
		this.inventoryService = inventoryService;
	}

	public List<OrderItem> save(final String orderId, final List<OrderItemDTO> items) {
		items.forEach(item -> this.inventoryService.incrementQuantity(item.getInventoryId(), item.getQuantity()));

		final List<OrderItem> orderItems = items.stream()
			.map(orderItem -> OrderItem.builder()
				.orderId(orderId)
				.quantity(orderItem.getQuantity())
				.status(OrderItemStatus.CREATED)
				.inventoryId(orderItem.getInventoryId())
				.clientUniqueReferenceId(orderItem.getClientReferenceId())
				.build())
			.collect(Collectors.toList());

		log.info("[STARTED] persisting items for order id: {}", orderId);
		final var persisted = this.repository.saveAll(orderItems);
		log.info("[COMPLETED] persisted items for order id: {}", orderId);
		return persisted;
	}

	public OrderItem updateStatus(final OrderItemStatus completedStatus, final String uniqueReferenceId) {
		final OrderItem orderItem = this.repository.findByClientUniqueReferenceId(uniqueReferenceId)
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
