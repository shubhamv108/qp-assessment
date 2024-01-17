package code.shubham.app.product.dao.entities;

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
@Table(name = "product_categories", indexes = { @Index(name = "index_product_categories_name", columnList = "name"), })
public class ProductCategory extends BaseAbstractAuditableEntity {

	@Column(length = 64, nullable = false)
	private String name;

	@Column(length = 256, nullable = false)
	private String description;

}