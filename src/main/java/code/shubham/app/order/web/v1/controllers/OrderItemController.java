package code.shubham.app.order.web.v1.controllers;

import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import code.shubham.app.order.services.OrderItemService;
import code.shubham.app.order.services.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/orders/{orderId}/items")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "OrderItem")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class OrderItemController {

	private final OrderService orderService;

	private final OrderItemService service;

	@Autowired
	public OrderItemController(final OrderService orderService, final OrderItemService service) {
		this.orderService = orderService;
		this.service = service;
	}

	@GetMapping
	public ResponseEntity<?> getAllOrderItems(@PathVariable("orderId") final String orderId,
			@RequestParam("userId") final String userId) {
		Utils.validateUserOrThrowException(userId);
		this.orderService.fetchByUserIdAndOrderOrderId(userId, orderId)
			.orElseThrow(() -> new InvalidRequestException("orderId", "No order found for oderId: %s", orderId));
		return ResponseUtils.getDataResponseEntity(HttpStatus.FOUND, this.service.fetchAllByOrderId(orderId));
	}

}
