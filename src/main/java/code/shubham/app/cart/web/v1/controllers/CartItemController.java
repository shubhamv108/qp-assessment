package code.shubham.app.cart.web.v1.controllers;

import code.shubham.app.cart.convertors.CartItemConvertor;
import code.shubham.app.cart.services.CartItemService;
import code.shubham.app.cart.web.v1.validators.AddCartItemRequestValidator;
import code.shubham.app.cart.web.v1.validators.UpdateCartItemQuantityRequestValidator;
import code.shubham.app.cartmodels.AddCartItemRequest;
import code.shubham.app.cartmodels.UpdateCartItemQuantityRequest;
import code.shubham.commons.annotations.Role;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.contexts.UserIDContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.Utils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/carts/{cartId}/items")
@Tag(name = "CartItem")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class CartItemController {

	private final CartItemService service;

	@Autowired
	public CartItemController(final CartItemService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity<?> add(@PathVariable(value = "cartId", required = false) final String cartId,
			@RequestBody final AddCartItemRequest request) {
		new AddCartItemRequestValidator().validateOrThrowException(request);
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, this.service.add(request, cartId, null));
	}

	@PatchMapping("/{cartItemId}")
	public ResponseEntity<?> updateQuantity(@PathVariable("cartId") final String cartId,
			@PathVariable("cartItemId") final String cartItemId,
			@RequestBody final UpdateCartItemQuantityRequest request) {
		new UpdateCartItemQuantityRequestValidator().validateOrThrowException(request);
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK,
				this.service.updateQuantity(request, cartItemId, cartId, null));
	}

	@SecurityRequirement(name = "BearerAuth")
	@Role("USER")
	@PostMapping("/users/{userId}")
	public ResponseEntity<?> add(@PathVariable(value = "cartId") final String cartId,
			@PathVariable(value = "userId") final String userId, @RequestBody final AddCartItemRequest request) {
		new AddCartItemRequestValidator().validateOrThrowException(request);
		Utils.validateUserOrThrowException(userId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED, this.service.add(request, cartId, userId));
	}

	@SecurityRequirement(name = "BearerAuth")
	@Role("USER")
	@PatchMapping("/{cartItemId}/users/{userId}")
	public ResponseEntity<?> updateQuantity(@PathVariable("cartId") final String cartId,
			@PathVariable("cartItemId") final String cartItemId, @PathVariable("userId") final String userId,
			@RequestBody final UpdateCartItemQuantityRequest request) {
		new UpdateCartItemQuantityRequestValidator().validateOrThrowException(request);
		Utils.validateUserOrThrowException(userId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK,
				this.service.updateQuantity(request, cartItemId, cartId, userId));
	}

}
