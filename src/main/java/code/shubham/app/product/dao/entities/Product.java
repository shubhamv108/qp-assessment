package code.shubham.app.product.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.Duration;

@SuperBuilder
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "products", indexes = { @Index(name = "index_products_name", columnList = "name"),
		@Index(name = "index_products_category_id", columnList = "categoryId"), })
public class Product extends BaseAbstractAuditableEntity {

	@Column(length = 64)
	private String name;

	@Column(length = 256)
	private String description;

	private Duration expirationDuration;

	@Column(length = 36)
	private String categoryId;

}