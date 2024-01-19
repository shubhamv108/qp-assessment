package code.shubham.app.supplier.web.v1.validators;

import code.shubham.app.suppliermodels.CreateSupplierRequest;
import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;

public class CreateSupplierRequestValidator extends AbstractRequestValidator<CreateSupplierRequest> {

	@Override
	public IValidator<CreateSupplierRequest> validate(final CreateSupplierRequest request) {
		super.validate(request);

		if (StringUtils.isEmpty(request.getName()))
			this.putMessage("name", MUST_NOT_BE_EMPTY, "name");
		if (StringUtils.isEmpty(request.getAddress()))
			this.putMessage("address", MUST_NOT_BE_EMPTY, "address");
		if (StringUtils.isEmpty(request.getPhone()))
			this.putMessage("phone", MUST_NOT_BE_EMPTY, "phone");
		if (this.isNotValidId(request.getUserId()))
			this.putMessage("userId", MUST_BE_VALID_ID, "userId");

		return this;
	}

}
