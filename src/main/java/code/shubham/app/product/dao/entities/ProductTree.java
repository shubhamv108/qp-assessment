package code.shubham.app.product.dao.entities;

import code.shubham.commons.dao.entities.base.BaseIdEntity;
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
@Table(name = "product_trees", indexes = { @Index(name = "index_product_tree_parentId", columnList = "parentId") })
public class ProductTree extends BaseIdEntity {

	@Column(length = 36, nullable = false)
	private String parentId;

	private String title;

}