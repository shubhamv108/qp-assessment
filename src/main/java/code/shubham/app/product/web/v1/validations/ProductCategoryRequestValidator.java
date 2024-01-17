package code.shubham.app.product.web.v1.validations;

import code.shubham.app.productmodels.ProductCategoryDTO;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;

public class ProductCategoryRequestValidator extends AbstractRequestValidator<ProductCategoryDTO> {

	@Override
	public IValidator<ProductCategoryDTO> validate(ProductCategoryDTO request) {
		super.validate(request);

		if (request.getName() == null)
			this.putMessage("name", MUST_NOT_BE_EMPTY, "name");
		if (request.getDescription() == null)
			this.putMessage("description", MUST_NOT_BE_EMPTY, "description");

		return this;
	}

}
