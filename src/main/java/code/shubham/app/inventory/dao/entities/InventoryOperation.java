package code.shubham.app.inventory.dao.entities;

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
@Table(name = "inventory_operations",
		indexes = { @Index(name = "index_inventory_operations_referenceId", columnList = "referenceId") },
		uniqueConstraints = { @UniqueConstraint(columnNames = { "referenceId", "type" }) })
public class InventoryOperation extends BaseAbstractAuditableEntity {

	@Column(nullable = false, length = 36, updatable = false)
	private String inventoryId;

	@Builder.Default
	private Integer quantity = 0;

	@Enumerated(EnumType.STRING)
	private InventoryOperationType type;

	@Column(nullable = false, length = 36, updatable = false)
	private String referenceId;

}