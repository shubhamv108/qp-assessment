package code.shubham.app.inventory.dao.repositories;

import code.shubham.app.inventory.dao.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

	Optional<Inventory> findByUserIdAndSupplierIdAndProductTreeId(String userId, String supplierId,
			Integer productTreeId);

	List<Inventory> findByProductTreeIdAndSupplierId(Integer productTreeId, String supplierId);

	List<Inventory> findByProductTreeId(Integer productTreeId);

	@Transactional
	@Modifying
	@Query(value = "update inventories set quantity = quantity - ? where product_tree_id = ?", nativeQuery = true)
	int updateQuantity(int number, Integer productTreeId);

	@Transactional
	@Modifying
	@Query(value = "update inventories set quantity = quantity - ? where id = ? AND quantity > 0", nativeQuery = true)
	int incrementQuantity(int number, String id);

	@Query(value = "select (quantity > ?) from inventories where id = ?", nativeQuery = true)
	int hasQuantity(int number, String id);

	Optional<Inventory> findByIdAndUserId(String inventoryId, String userId);

}
