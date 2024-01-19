package code.shubham.app.cart.web.v1.validators;

import code.shubham.app.cartmodels.UpdateCartItemQuantityRequest;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;

public class UpdateCartItemQuantityRequestValidator extends AbstractRequestValidator<UpdateCartItemQuantityRequest> {

	@Override
	public IValidator<UpdateCartItemQuantityRequest> validate(final UpdateCartItemQuantityRequest request) {
		super.validate(request);

		if (request.getIncrementBy() == null)
			this.putMessage("incrementBy", MUST_NOT_BE_EMPTY, "incrementBy");
		else if (request.getIncrementBy() <= 0)
			this.putMessage("incrementBy", "Must be greater then 0");

		return this;
	}

}
