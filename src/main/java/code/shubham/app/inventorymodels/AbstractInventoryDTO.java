package code.shubham.app.inventorymodels;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractInventoryDTO {

	@NotNull
	@NotEmpty
	private Integer productTreeId;

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
