package code.shubham.app.inventory.dao.repositories;

import code.shubham.app.inventory.dao.entities.Inventory;
import code.shubham.app.inventory.dao.entities.InventoryOperation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryOperationRepository extends JpaRepository<InventoryOperation, String> {

	List<InventoryOperation> findAllByReferenceId(String referenceId);

}
