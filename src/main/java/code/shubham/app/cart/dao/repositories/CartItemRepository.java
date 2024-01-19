package code.shubham.app.cart.dao.repositories;

import code.shubham.app.cart.dao.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, String> {

	@Transactional
	@Modifying
	@Query(value = "UPDATE cart_items SET quantity = quantity + ? WHERE id = ? AND cart_id = ?", nativeQuery = true)
	int updateCartItemQuantity(Integer incrementBy, String id, String cartId);

	List<CartItem> findAllByCartId(String cartId);

	Optional<CartItem> findByCartIdAndInventoryId(String cartId, String inventoryId);

}
