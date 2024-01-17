package code.shubham.app.ordermodels;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class OrderDTO {

	@NotNull
	@NotEmpty
	@Min(6)
	@Max(40)
	private String orderId;

	@NotEmpty
	private String customerId;

	@NotEmpty
	private String customerType;

	@NotNull
	@NotEmpty
	@Min(6)
	@Max(40)
	private String userId;

	@NotNull
	@NotEmpty
	private String status;

	@NotNull
	@NotEmpty
	@Min(6)
	@Max(40)
	private String uniqueReferenceId;

}
