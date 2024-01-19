package code.shubham.app.cartmodels;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class CartItemDTO {

	private String cartItemId;

	private String cartId;

	private String inventoryId;

	private int quantity;

}
