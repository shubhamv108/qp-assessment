package code.shubham.app.inventory.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.*;
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
@Table(name = "inventories",
		indexes = { @Index(name = "index_inventory_userId", columnList = "userId"),
				@Index(name = "index_inventory__supplierId", columnList = "supplierId"),
				@Index(name = "index_inventory_productId", columnList = "productId"), },
		uniqueConstraints = { @UniqueConstraint(columnNames = { "productTreeId, supplierId" }) })
public class Inventory extends BaseAbstractAuditableEntity {

	@Column(columnDefinition = "INT CHECK (quantity >= 0)")
	private int quantity;

	@Column(columnDefinition = "DECIMAL(10,2) CHECK (price >= 0)")
	private double price;

	@Column(nullable = false, length = 36, updatable = false)
	private Integer productTreeId;

	@Column(nullable = false, length = 36, updatable = false)
	private String supplierId;

	@Column(nullable = false, length = 36, updatable = false)
	private String userId;

}