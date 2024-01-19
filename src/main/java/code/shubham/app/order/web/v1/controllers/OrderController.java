package code.shubham.app.order.web.v1.controllers;

import code.shubham.app.cart.dao.entities.CartItem;
import code.shubham.app.cart.services.CartItemService;
import code.shubham.app.cartcommons.ICartItemService;
import code.shubham.app.order.dao.entities.Order;
import code.shubham.app.order.dao.entities.OrderItem;
import code.shubham.app.order.web.v1.validators.CreateOrderRequestValidator;
import code.shubham.app.ordermodels.CreateOrderCommand;
import code.shubham.app.ordermodels.CreateOrderRequest;
import code.shubham.app.ordermodels.OrderItemDTO;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.UUIDUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.app.order.services.OrderService;
import code.shubham.app.ordermodels.GetAllOrdersResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/orders")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Order")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class OrderController {

	private final OrderService service;

	private final ICartItemService cartItemService;

	@Autowired
	public OrderController(final OrderService service, final CartItemService cartItemService) {
		this.service = service;
		this.cartItemService = cartItemService;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody final CreateOrderRequest request) {
		new CreateOrderRequestValidator().validateOrThrowException(request);
		Utils.validateUserOrThrowException(request.getUserId());
		final List<CartItem> items = this.cartItemService.fetchAllByCartIdAndUserId(request.getCartId(),
				request.getUserId());

		final Order order = this.service.create(CreateOrderCommand.builder()
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
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, order);
	}

	@GetMapping
	public ResponseEntity<?> getAllOrders(@RequestParam("userId") final String userId) {
		Utils.validateUserOrThrowException(userId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND,
				GetAllOrdersResponse.builder()
					.orders(this.service.fetchAllByUserId(userId)
						.stream()
						.map(order -> GetAllOrdersResponse.Order.builder()
							.orderId(order.getId())
							.status(order.getStatus().name())
							.userId(order.getUserId())
							.build())
						.toList())
					.build());
	}

}
