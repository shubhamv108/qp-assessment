package code.shubham.app.cartcommons;

import code.shubham.app.cart.dao.entities.CartItem;

import java.util.List;

public interface ICartItemService {

	List<CartItem> fetchAllByCartId(String cartId);

	List<CartItem> fetchAllByCartIdAndUserId(String cartId, String userId);

}
