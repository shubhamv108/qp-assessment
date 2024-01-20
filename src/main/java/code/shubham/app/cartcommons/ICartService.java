package code.shubham.app.cartcommons;

import code.shubham.app.cart.dao.entities.CartItem;

import java.util.Collection;
import java.util.List;

public interface ICartService {

	List<CartItem> fetchAllByCartIdAndUserId(String cartId, String userId);

	void clear(Collection<String> itemIds);

}
