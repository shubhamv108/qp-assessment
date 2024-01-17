package code.shubham.app.order.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "orders", indexes = { @Index(name = "index_orders_user_id", columnList = "userId") })
public class Order extends BaseAbstractAuditableEntity {

	@Column(nullable = false)
	private String customerId;

	@Column(nullable = false)
	private String customerType;

	@Column(nullable = false)
	private String userId;

	@Enumerated(EnumType.STRING)
	private OrderStatus status;

	@Column(nullable = false, unique = true)
	private String uniqueReferenceId;

}
