package code.shubham.app.cartmodels;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateCartItemQuantityRequest {

	private Integer incrementBy;

	private String userId;

}
