package code.shubham.app.order.dao.repositories;

import code.shubham.app.order.dao.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

	List<Order> findAllByUserId(String userId);

	Optional<Order> findByIdAndUserId(String id, String userId);

	Optional<Order> findById(String orderId);

}
