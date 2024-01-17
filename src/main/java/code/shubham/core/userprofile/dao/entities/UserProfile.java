package code.shubham.core.userprofile.dao.entities;

import code.shubham.commons.dao.entities.base.BaseAbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "user_profiles")
public class UserProfile extends BaseAbstractAuditableEntity {

	@Column(nullable = false, unique = true)
	private String userId;

	private String address;

}
