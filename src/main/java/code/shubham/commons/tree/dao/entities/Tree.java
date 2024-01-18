package code.shubham.commons.tree.dao.entities;

import code.shubham.commons.dao.entities.base.BaseIntegerIdEntity;
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
@Table(name = "trees", indexes = { @Index(name = "index_trees_parentId", columnList = "parentId") })
public class Tree extends BaseIntegerIdEntity {

	private Integer parentId;

	@Column(length = 64)
	private String title;

}