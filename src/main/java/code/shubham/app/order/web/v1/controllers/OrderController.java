package code.shubham.app.order.web.v1.controllers;

import code.shubham.app.order.orchestrator.CreateOrderOrchestrator;
import code.shubham.app.order.services.OrderService;
import code.shubham.app.order.web.v1.validators.CreateOrderRequestValidator;
import code.shubham.app.ordermodels.CreateOrderRequest;
import code.shubham.app.ordermodels.GetAllOrdersResponse;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/orders")
@SecurityRequirement(name = "BearerAuth")
@Tag(name = "Order")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class OrderController {

	private final OrderService service;

	private final ApplicationContext applicationContext;

	@Autowired
	public OrderController(final OrderService service, final ApplicationContext applicationContext) {
		this.service = service;
		this.applicationContext = applicationContext;
	}

	@PostMapping
	public ResponseEntity<?> create(@RequestBody final CreateOrderRequest request) {
		new CreateOrderRequestValidator().validateOrThrowException(request);
		Utils.validateUserOrThrowException(request.getUserId());
		final CreateOrderOrchestrator orchestrator = this.applicationContext.getBean("CreateOrderOrchestrator",
				CreateOrderOrchestrator.class);
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, orchestrator.orchestrate(request));
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
