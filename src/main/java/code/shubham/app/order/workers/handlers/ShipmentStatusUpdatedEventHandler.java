package code.shubham.app.order.workers.handlers;

import code.shubham.commons.handlers.EventHandler;
import code.shubham.commons.models.Event;
import code.shubham.commons.utils.JsonUtils;
import code.shubham.app.order.dao.entities.OrderItem;
import code.shubham.app.order.dao.entities.OrderItemStatus;
import code.shubham.app.order.dao.entities.OrderStatus;
import code.shubham.app.order.services.OrderItemService;
import code.shubham.app.order.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
// @Scope("prototype")
// @Component("ShipmentStatusUpdatedEventHandler")
public class ShipmentStatusUpdatedEventHandler implements EventHandler {

	private final OrderService service;

	private final OrderItemService orderItemService;

	@Autowired
	public ShipmentStatusUpdatedEventHandler(final OrderService service, final OrderItemService orderItemService) {
		this.service = service;
		this.orderItemService = orderItemService;
	}

	@Override
	public Object handle(final Event event) {
		log.debug(String.format("Event Data: %s", event.getData()));
		final Map<String, Object> data = JsonUtils.as(event.getData(), HashMap.class);
		final OrderItem orderItem = this.getCompletedOrderProductStatusFromShipmentStatus((String) data.get("status"))
			.map(orderItemStatus -> this.orderItemService.updateStatus(orderItemStatus, event.getUniqueReferenceId()))
			.orElseGet(() -> {
				log.info("[SKIP] Event skipped");
				return null;
			});

		if (OrderItemStatus.COMPLETED.name().equals(orderItem.getStatus().name()))
			return this.service.updateStatus((String) data.get("orderId"), OrderStatus.CREATED);

		return orderItem;
	}

	private Optional<OrderItemStatus> getCompletedOrderProductStatusFromShipmentStatus(final String shipmentStatus) {
		switch (shipmentStatus) {
			case "PREPARE_TO_DISPATCH":
				return Optional.ofNullable(OrderItemStatus.CREATED);
			case "DELIVERED":
				return Optional.ofNullable(OrderItemStatus.SHIPPED);
		}
		return Optional.empty();
	}

}
