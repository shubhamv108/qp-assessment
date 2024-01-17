package code.shubham.app.supplier.dao.repositories;

import code.shubham.app.supplier.dao.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepositories extends JpaRepository<Supplier, String> {

}
