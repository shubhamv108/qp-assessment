package code.shubham.app.cart.services;

import code.shubham.app.cart.convertors.CartItemConvertor;
import code.shubham.app.cart.dao.entities.Cart;
import code.shubham.app.cart.dao.entities.CartItem;
import code.shubham.app.cart.dao.repositories.CartItemRepository;
import code.shubham.app.cart.dao.repositories.CartRepository;
import code.shubham.app.cartcommons.ICartItemService;
import code.shubham.app.cartmodels.AddCartItemRequest;
import code.shubham.app.cartmodels.CartItemDTO;
import code.shubham.app.cartmodels.UpdateCartItemQuantityRequest;
import code.shubham.app.inventory.services.InventoryService;
import code.shubham.commons.exceptions.InvalidRequestException;
import code.shubham.commons.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartItemService implements ICartItemService {

	private final CartRepository cartRepository;

	private final CartItemRepository cartItemRepository;

	private final InventoryService inventoryService;

	@Autowired
	public CartItemService(final CartRepository cartRepository, final CartItemRepository cartItemRepository,
			final InventoryService inventoryService) {
		this.cartRepository = cartRepository;
		this.cartItemRepository = cartItemRepository;
		this.inventoryService = inventoryService;
	}

	@Override
	public List<CartItem> fetchAllByCartId(final String cartId) {
		return this.cartItemRepository.findAllByCartId(cartId);
	}

	@Override
	public List<CartItem> fetchAllByCartIdAndUserId(final String cartId, final String userId) {
		return this.cartRepository.findByIdAndUserId(cartId, userId)
			.map(cart -> this.cartItemRepository.findAllByCartId(cart.getId()))
			.orElse(null);
	}

	public CartItemDTO add(final AddCartItemRequest request, final String cartId) {
		Cart cart;
		if (StringUtils.isEmpty(cartId))
			cart = this.cartRepository.save(Cart.builder().userId(request.getUserId()).build());
		else
			cart = this.cartRepository.findByIdAndUserId(cartId, request.getUserId())
				.orElseThrow(() -> new InvalidRequestException("cartId", "invalid cart id in request"));

		final Optional<CartItem> existingItem = this.cartItemRepository.findByCartIdAndInventoryId(cartId,
				request.getInventoryId());

		if (existingItem.isPresent())
			return CartItemConvertor.convert(this.updateQuantity(existingItem.get(), 1));

		this.inventoryService.hasQuantity(request.getInventoryId(), 1);

		return CartItemConvertor.convert(this.cartItemRepository
			.save(CartItem.builder().cartId(cart.getId()).inventoryId(request.getInventoryId()).quantity(1).build()));
	}

	public CartItemDTO updateQuantity(final UpdateCartItemQuantityRequest request, final String cartItemId,
			final String cartId) {
		this.cartRepository.findByIdAndUserId(cartId, request.getUserId())
			.orElseThrow(() -> new InvalidRequestException("cartId", "invalid cart id in request"));
		final CartItem existingItem = this.cartItemRepository.findById(cartItemId)
			.orElseThrow(() -> new InvalidRequestException("cartItemId", "No such cart item found: %s", cartItemId));
		return CartItemConvertor.convert(this.updateQuantity(existingItem, request.getIncrementBy()));
	}

	public CartItem updateQuantity(final CartItem existingItem, final int incrementBy) {
		this.inventoryService.hasQuantity(existingItem.getInventoryId(), existingItem.getQuantity() + incrementBy);
		this.updateQuantity(incrementBy, existingItem.getId(), existingItem.getCartId());
		return this.cartItemRepository.findById(existingItem.getId())
			.orElseThrow(() -> new InvalidRequestException("cartItemId", "No such cart item found: %s",
					existingItem.getId()));
	}

	public boolean updateQuantity(final Integer incrementBy, final String cartItemId, final String cartId) {
		return this.cartItemRepository.updateCartItemQuantity(incrementBy, cartItemId, cartId) == 1;
	}

}
