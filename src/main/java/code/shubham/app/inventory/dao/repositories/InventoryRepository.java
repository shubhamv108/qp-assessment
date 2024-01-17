package code.shubham.app.inventory.dao.repositories;

import code.shubham.app.inventory.dao.entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, String> {

	Optional<Inventory> findByUserIdAndSupplierIdAndProductTreeId(String userId, String supplierId,
			String productTreeId);

	@Transactional
	@Modifying
	@Query(value = "update inventories set quantity = quantity - ? where product_tree_id = ?", nativeQuery = true)
	int updateQuantity(int number, String productTreeId);

}
