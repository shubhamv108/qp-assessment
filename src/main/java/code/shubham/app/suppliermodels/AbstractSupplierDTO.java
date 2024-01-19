package code.shubham.app.suppliermodels;

import jakarta.validation.constraints.Max;
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
public abstract class AbstractSupplierDTO {

	@NotNull
	@NotEmpty
	@Min(4)
	@Min(128)
	private String name;

	@Min(200)
	private String address;

	@NotNull
	@NotEmpty
	@Min(10)
	@Min(12)
	private String phone;

	@NotEmpty
	@NotNull
	@Min(36)
	@Max(36)
	private String userId;

}
