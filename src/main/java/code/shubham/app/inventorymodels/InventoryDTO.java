package code.shubham.app.inventorymodels;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class InventoryDTO {

	@NotNull
	@NotEmpty
	private String productTreeId;

	@NotNull
	@NotEmpty
	private String supplierId;

	@NotNull
	@NotEmpty
	private String userId;

	@NotNull
	@NotEmpty
	@Min(0)
	private Double price;

	@NotNull
	@NotEmpty
	@Min(0)
	private Integer quantity;

}
