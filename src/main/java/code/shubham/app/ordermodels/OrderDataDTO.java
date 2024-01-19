package code.shubham.app.ordermodels;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class OrderDataDTO {

	@NotNull
	private OrderDTO order;

	private List<OrderItemDTO> items;

}
