package code.shubham.app.product.web.v1.validatiors;

import code.shubham.app.productmodels.PutProductCategoryRequest;
import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;

public class ProductCategoryRequestValidator extends AbstractRequestValidator<PutProductCategoryRequest> {

	@Override
	public IValidator<PutProductCategoryRequest> validate(PutProductCategoryRequest request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getName()))
			this.putMessage("name", MUST_NOT_BE_EMPTY, "name");

		return this;
	}

}
