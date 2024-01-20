package code.shubham.app.cart.services;

import code.shubham.app.cart.dao.entities.Cart;
import code.shubham.app.cart.dao.entities.CartItem;
import code.shubham.app.cart.dao.repositories.CartRepository;
import code.shubham.app.cartcommons.ICartService;
import code.shubham.commons.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Slf4j
@Service
public class CartService implements ICartService {

	private final CartRepository repository;

	private final CartItemService cartItemService;

	@Autowired
	public CartService(final CartRepository repository, final CartItemService cartItemService) {
		this.repository = repository;
		this.cartItemService = cartItemService;
	}

	public Cart update(final String cartId, final String userId) {
		final Cart cart = this.repository.findById(cartId)
			.orElseThrow(() -> new InvalidRequestException("cartId", "no cart found with id: %s", cartId));
		if (cart.getUserId() != null)
			throw new InvalidRequestException("userId", "userId already set for cart");
		cart.setUserId(userId);
		return this.repository.save(cart);
	}

	@Override
	public List<CartItem> fetchAllByCartIdAndUserId(final String cartId, final String userId) {
		return this.repository.findByIdAndUserId(cartId, userId)
			.map(cart -> this.cartItemService.fetchAllByCartId(cart.getId()))
			.orElse(null);
	}

	@Override
	public void clear(final Collection<String> itemIds) {
		this.cartItemService.clear(itemIds);
	}

}
