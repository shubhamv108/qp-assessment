package code.shubham.app.cart.web.v1.validators;

import code.shubham.app.cartmodels.AddCartItemRequest;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;

public class AddCartItemRequestValidator extends AbstractRequestValidator<AddCartItemRequest> {

	@Override
	public IValidator<AddCartItemRequest> validate(final AddCartItemRequest request) {
		super.validate(request);

		if (this.isNotValidId(request.getInventoryId()))
			this.putMessage("inventoryId", MUST_BE_VALID_ID, "inventoryId");

		return this;
	}

}
