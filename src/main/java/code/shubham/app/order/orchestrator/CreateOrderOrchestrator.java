package code.shubham.app.order.orchestrator;

import code.shubham.app.cart.dao.entities.CartItem;
import code.shubham.app.cart.services.CartService;
import code.shubham.app.cartcommons.ICartService;
import code.shubham.app.inventory.services.InventoryService;
import code.shubham.app.inventorycommons.IInventoryService;
import code.shubham.app.order.dao.entities.Order;
import code.shubham.app.order.dao.entities.OrderStatus;
import code.shubham.app.order.services.OrderService;
import code.shubham.app.ordermodels.CreateOrderCommand;
import code.shubham.app.ordermodels.CreateOrderRequest;
import code.shubham.app.ordermodels.OrderDataDTO;
import code.shubham.app.ordermodels.OrderItemDTO;
import code.shubham.commons.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Scope("prototype")
@Component("CreateOrderOrchestrator")
public class CreateOrderOrchestrator {

	private final OrderService service;

	private final ICartService cartService;

	@Autowired
	public CreateOrderOrchestrator(final OrderService service, final CartService cartService) {
		this.service = service;
		this.cartService = cartService;
	}

	@Transactional
	public OrderDataDTO orchestrate(final CreateOrderRequest request) {
		final List<CartItem> items = this.cartService.fetchAllByCartIdAndUserId(request.getCartId(),
				request.getUserId());

		final OrderDataDTO order = this.service.create(CreateOrderCommand.builder()
			.clientReferenceId(request.getClientReferenceId())
			.userId(request.getUserId())
			.customerId(request.getUserId())
			.customerType("BUYER")
			.items(items.stream()
				.map(cartItem -> OrderItemDTO.builder()
					.clientReferenceId(
							UUIDUtils.uuid5(request.getClientReferenceId() + "_" + cartItem.getInventoryId()))
					.quantity(cartItem.getQuantity())
					.inventoryId(cartItem.getInventoryId())
					.build())
				.toList())
			.build());

		this.cartService.clear(items.stream().map(CartItem::getId).toList());
		return order;
	}

}
