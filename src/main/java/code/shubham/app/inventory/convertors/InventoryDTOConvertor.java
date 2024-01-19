package code.shubham.app.inventory.convertors;

import code.shubham.app.inventory.dao.entities.Inventory;
import code.shubham.app.inventorymodels.InventoryDTO;

public class InventoryDTOConvertor {

	public static InventoryDTO convert(final Inventory inventory) {
		return InventoryDTO.builder()
			.inventoryId(inventory.getId())
			.productTreeId(inventory.getProductTreeId())
			.price(inventory.getPrice())
			.quantity(inventory.getQuantity())
			.supplierId(inventory.getSupplierId())
			.userId(inventory.getUserId())
			.build();
	}

}
