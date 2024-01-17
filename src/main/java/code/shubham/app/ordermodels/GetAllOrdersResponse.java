package code.shubham.app.ordermodels;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class GetAllOrdersResponse {

	@Builder
	@Data
	public static class Order {

		private String orderId;

		private String userId;

		private String status;

	}

	private List<Order> orders;

}
