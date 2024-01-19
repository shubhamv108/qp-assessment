package code.shubham.app.ordermodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class CreateOrderCommand {

	@NotNull
	private List<OrderItemDTO> items;

	@NotNull
	@NotEmpty
	@Min(10)
	@Max(36)
	private String userId;

	@Min(10)
	@Max(36)
	private String customerId;

	@Min(10)
	@Max(36)
	private String customerType;

	@NotNull
	@NotEmpty
	@Min(10)
	@Max(36)
	private String clientReferenceId;

}
