package code.shubham.app.order.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "order_items", indexes = { @Index(name = "index_order_items_order_id", columnList = "orderId") })
public class OrderItem extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private String orderId;

	@Column(nullable = false, length = 36)
	private String inventoryId;

	@Column(nullable = false, columnDefinition = "INT CHECK (quantity > 0)")
	private int quantity;

	private OrderItemStatus status;

	@Column(nullable = false, unique = true)
	private String clientUniqueReferenceId;

}
