package code.shubham.app.inventorycommons;

import org.springframework.transaction.annotation.Transactional;

public interface IInventoryService {

	@Transactional
	boolean applyQuantityOperation(String id, int quantity, String referenceId);

	@Transactional
	boolean revertQuantityOperation(String referenceId);

	boolean hasQuantity(String id, int quantity);

}
