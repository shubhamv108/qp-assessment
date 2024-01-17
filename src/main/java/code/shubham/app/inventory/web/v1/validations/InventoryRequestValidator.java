package code.shubham.app.inventory.web.v1.validations;

import code.shubham.app.inventorymodels.InventoryDTO;
import code.shubham.commons.validators.AbstractRequestValidator;
import code.shubham.commons.validators.IValidator;

public class InventoryRequestValidator extends AbstractRequestValidator<InventoryDTO> {

	@Override
	public IValidator<InventoryDTO> validate(InventoryDTO inventoryDTO) {
		super.validate(inventoryDTO);

		if (inventoryDTO.getProductTreeId() == null)
			this.putMessage("productTreeId", MUST_NOT_BE_EMPTY, "productTreeId");
		if (inventoryDTO.getSupplierId() == null)
			this.putMessage("supplierId", MUST_NOT_BE_EMPTY, "supplierId");
		if (inventoryDTO.getPrice() == null)
			this.putMessage("price", MUST_NOT_BE_EMPTY, "price");
		if (inventoryDTO.getQuantity() == null)
			this.putMessage("quantity", MUST_NOT_BE_EMPTY, "quantity");
		if (inventoryDTO.getUserId() == null)
			this.putMessage("userId", MUST_NOT_BE_EMPTY, "userId");

		return this;
	}

}
