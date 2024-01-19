package code.shubham.app.cart.dao.entities;

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
@Table(name = "cart_items", indexes = { @Index(name = "index_cart_items_cartId", columnList = "cartId") },
		uniqueConstraints = { @UniqueConstraint(columnNames = { "cartId, inventoryId" }) })
public class CartItem extends BaseAbstractAuditableEntity {

	// @Column(columnDefinition = "INT CHECK (quantity > 0)")
	private Integer quantity;

	@Column(nullable = false)
	private String inventoryId;

	@Column(nullable = false)
	private String cartId;

}
