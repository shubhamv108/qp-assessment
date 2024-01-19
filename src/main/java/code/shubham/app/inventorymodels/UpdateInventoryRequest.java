package code.shubham.app.inventorymodels;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Builder
@Getter
@Setter
@AllArgsConstructor
public class UpdateInventoryRequest {

	@NotNull
	@NotEmpty
	private String userId;

	@NotNull
	@NotEmpty
	@Min(0)
	private Double price;

	private Integer quantity;

}
