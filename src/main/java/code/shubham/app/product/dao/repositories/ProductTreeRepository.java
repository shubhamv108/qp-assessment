package code.shubham.app.product.dao.repositories;

import code.shubham.app.product.dao.entities.ProductTree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductTreeRepository extends JpaRepository<ProductTree, String> {

	@Query(value = "WITH RECURSIVE cte AS (SELECT title as title, id AS path FROM product_trees WHERE parent_id = ? UNION ALL SELECT child.title as title, CONCAT(parent.id,'/',child.id) AS id FROM product_trees parent , product_trees child WHERE child.parent_id = parent.id ) SELECT * FROM cte;",
			nativeQuery = true)
	List<ProductTree> getTrees(String rootProductId);

}
