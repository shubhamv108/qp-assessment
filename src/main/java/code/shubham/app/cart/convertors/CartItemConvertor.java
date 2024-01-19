package code.shubham.app.cart.convertors;

import code.shubham.app.cart.dao.entities.CartItem;
import code.shubham.app.cartmodels.CartItemDTO;

public class CartItemConvertor {

	public static CartItemDTO convert(final CartItem item) {
		return CartItemDTO.builder()
			.cartItemId(item.getId())
			.inventoryId(item.getInventoryId())
			.quantity(item.getQuantity())
			.cartId(item.getCartId())
			.build();
	}

}
