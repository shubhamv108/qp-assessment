package code.shubham.app.inventorycommons;

public interface IInventoryService {

	boolean incrementQuantity(String id, int quantity);

	boolean hasQuantity(String id, int quantity);

}
