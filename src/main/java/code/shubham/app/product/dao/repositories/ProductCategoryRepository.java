package code.shubham.app.product.dao.repositories;

import code.shubham.app.product.dao.entities.Product;
import code.shubham.app.product.dao.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

	Optional<ProductCategory> findByName(String name);

}
