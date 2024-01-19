package code.shubham.app.inventory.web.v1.validations;

import code.shubham.app.inventorymodels.CreateInventoryRequest;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;

public class CreteInventoryRequestValidator extends AbstractRequestValidator<CreateInventoryRequest> {

	@Override
	public IValidator<CreateInventoryRequest> validate(CreateInventoryRequest inventoryRequest) {
		super.validate(inventoryRequest);

		if (inventoryRequest.getProductTreeId() == null)
			this.putMessage("productTreeId", MUST_NOT_BE_EMPTY, "productTreeId");
		if (inventoryRequest.getSupplierId() == null)
			this.putMessage("supplierId", MUST_NOT_BE_EMPTY, "supplierId");
		if (this.isInvalidPrice(inventoryRequest.getPrice()))
			this.putMessage("price", MUST_NOT_BE_LESS_THAN_ZERO, "price");
		if (this.isInvalidQuantity(inventoryRequest.getQuantity()))
			this.putMessage("quantity", MUST_NOT_BE_LESS_THAN_ZERO, "quantity");
		if (inventoryRequest.getUserId() == null)
			this.putMessage("userId", MUST_NOT_BE_EMPTY, "userId");

		return this;
	}

}
