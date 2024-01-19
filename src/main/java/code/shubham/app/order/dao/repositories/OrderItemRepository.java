package code.shubham.app.order.dao.repositories;

import code.shubham.app.order.dao.entities.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, String> {

	List<OrderItem> findAllByOrderId(String orderId);

	Optional<OrderItem> findByClientUniqueReferenceId(String uniqueReferenceId);

}
