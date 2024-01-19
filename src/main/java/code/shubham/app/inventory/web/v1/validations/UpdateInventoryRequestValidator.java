package code.shubham.app.inventory.web.v1.validations;

import code.shubham.app.inventorymodels.UpdateInventoryRequest;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;

public class UpdateInventoryRequestValidator extends AbstractRequestValidator<UpdateInventoryRequest> {

	@Override
	public IValidator<UpdateInventoryRequest> validate(final UpdateInventoryRequest inventoryRequest) {
		super.validate(inventoryRequest);

		if (this.isUpdatable(inventoryRequest.getPrice()) && this.isInvalidPrice(inventoryRequest.getPrice()))
			this.putMessage("price", MUST_NOT_BE_LESS_THAN_ZERO, "price");
		if (this.isUpdatable(inventoryRequest.getQuantity()) && this.isInvalidQuantity(inventoryRequest.getQuantity()))
			this.putMessage("quantity", MUST_NOT_BE_LESS_THAN_ZERO, "quantity");
		if (this.isNotValidId(inventoryRequest.getUserId()))
			this.putMessage("userId", MUST_BE_VALID_ID, "userId");

		return this;
	}

}
