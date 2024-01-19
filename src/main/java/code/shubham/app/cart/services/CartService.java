package code.shubham.app.cart.services;

import code.shubham.app.cart.dao.entities.Cart;
import code.shubham.app.cart.dao.repositories.CartRepository;
import code.shubham.commons.exceptions.InvalidRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartService {

	private final CartRepository repository;

	@Autowired
	public CartService(CartRepository repository) {
		this.repository = repository;
	}

	public Cart update(final String cartId, final String userId) {
		final Cart cart = this.repository.findById(cartId)
			.orElseThrow(() -> new InvalidRequestException("cartId", "no cart found with id: %s", cartId));
		if (cart.getUserId() != null)
			throw new InvalidRequestException("userId", "userId already set for cart");
		cart.setUserId(userId);
		return this.repository.save(cart);
	}

}
