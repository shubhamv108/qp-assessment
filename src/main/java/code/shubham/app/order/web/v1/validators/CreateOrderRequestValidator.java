package code.shubham.app.order.web.v1.validators;

import code.shubham.app.ordermodels.CreateOrderRequest;
import code.shubham.commons.utils.StringUtils;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;

public class CreateOrderRequestValidator extends AbstractRequestValidator<CreateOrderRequest> {

	@Override
	public IValidator<CreateOrderRequest> validate(final CreateOrderRequest request) {
		super.validate(request);

		if (this.isNotValidId(request.getUserId()))
			this.putMessage("userId", MUST_NOT_BE_EMPTY, "userId");
		if (this.isNotValidId(request.getClientReferenceId()))
			this.putMessage("clientReferenceId", MUST_NOT_BE_EMPTY, "clientReferenceId");
		if (this.isNotValidId(request.getCartId()))
			this.putMessage("cartId", MUST_NOT_BE_EMPTY, "cartId");

		return this;
	}

}
