package code.shubham.app.cart.web.v1.controllers;

import code.shubham.app.cart.convertors.CartItemConvertor;
import code.shubham.app.cart.services.CartItemService;
import code.shubham.app.cart.services.CartService;
import code.shubham.app.cart.web.v1.validators.AddCartItemRequestValidator;
import code.shubham.app.cart.web.v1.validators.UpdateCartItemQuantityRequestValidator;
import code.shubham.app.cartmodels.AddCartItemRequest;
import code.shubham.app.cartmodels.UpdateCartItemQuantityRequest;
import code.shubham.commons.annotations.Role;
import code.shubham.commons.contexts.RoleContextHolder;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.ResponseUtils;
import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.utils.Utils;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/carts")
@Tag(name = "Cart")
@ConditionalOnProperty(prefix = "service", name = "module", havingValue = "web")
public class CartController {

	private final CartService service;

	@Autowired
	public CartController(final CartService service) {
		this.service = service;
	}

	@SecurityRequirement(name = "BearerAuth")
	@Role("USER")
	@PatchMapping("/{cartId}")
	public ResponseEntity<?> update(@PathVariable("cartId") final String cartId,
			@RequestBody final UpdateCartItemQuantityRequest request) {
		if (StringUtils.isEmpty(request.getUserId()))
			throw new InvalidRequestException("userId", "userId must not be empty");
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK, this.service.update(cartId, request.getUserId()));
	}

	@GetMapping("/{cartId}")
	public ResponseEntity<?> getAll(@PathVariable("cartId") final String cartId) {
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK,
				Optional.ofNullable(this.service.fetchAllByCartIdAndUserId(cartId, null))
					.map(items -> items.stream().map(CartItemConvertor::convert).toList())
					.orElse(List.of()));
	}

	@SecurityRequirement(name = "BearerAuth")
	@Role("USER")
	@GetMapping("/{cartId}/users/{userId}")
	public ResponseEntity<?> getAll(@PathVariable("cartId") final String cartId,
			@PathVariable("userId") final String userId) {
		Utils.validateUserOrThrowException(userId);
		return ResponseUtils.getDataResponseEntity(HttpStatus.OK,
				this.service.fetchAllByCartIdAndUserId(cartId, userId)
					.stream()
					.map(CartItemConvertor::convert)
					.toList());
	}

}
