package code.shubham.app.cartmodels;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateCartRequest {

	private String userId;

}
