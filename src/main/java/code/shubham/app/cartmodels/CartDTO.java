package code.shubham.app.cartmodels;

import lombok.Builder;

import java.util.List;

@Builder

public class CartDTO {

	private String cartId;

	private List<AddCartItemRequest> cartItems;

}
