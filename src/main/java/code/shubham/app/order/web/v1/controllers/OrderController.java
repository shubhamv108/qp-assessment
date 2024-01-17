package code.shubham.app.order.web.v1.controllers;

import code.shubham.commons.utils.ResponseUtils;
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

@RestController
@RequestMapping("/v1/orders")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Order")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class OrderController {

	private final OrderService service;

	@Autowired
	public OrderController(final OrderService service) {
		this.service = service;
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
