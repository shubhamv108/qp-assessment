package code.shubham.app.cart.dao.repositories;

import code.shubham.app.cart.dao.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, String> {

	Optional<Cart> findByIdAndUserId(String cartId, String userId);

}
