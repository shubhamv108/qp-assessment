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
@Table(name = "carts", indexes = { @Index(name = "index_carts_userId", columnList = "userId") })
public class Cart extends BaseAbstractAuditableEntity {

	@Column
	private String userId;

}
