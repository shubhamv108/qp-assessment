package code.shubham.app.inventorycommons;

public interface IInventoryService {

	boolean updateQuantity(String productTreeId, String supplierId, String userId, int quantity);

}
