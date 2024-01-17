package code.shubham.app.supplier.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "suppliers", indexes = { @Index(name = "index_supplier_userId", columnList = "userId") })
public class Supplier extends BaseAbstractAuditableEntity {

	@Column(length = 64, nullable = false)
	private String name;

	@Column(length = 200)
	private String address;

	@Column(length = 12)
	private String phone;

	@Column(nullable = false, length = 36, updatable = false)
	private String userId;

}