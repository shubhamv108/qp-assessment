package code.shubham.app.productmodels;

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
public class PutProductCategoryRequest {

	@NotNull
	@NotEmpty
	@Min(3)
	@Max(64)
	private final String name;

	@Max(256)
	private final String description;

}
