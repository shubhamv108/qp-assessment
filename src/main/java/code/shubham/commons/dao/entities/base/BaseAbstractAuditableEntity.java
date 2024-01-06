package code.shubham.commons.dao.entities.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = { "createdAt", "updatedAt" }, allowGetters = true)
public abstract class BaseAbstractAuditableEntity extends BaseIdEntity {

	private static final long serialVersionUID = 8953324502234883513L;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false, updatable = false)
	@CreatedDate
	private Date createdAt;

	@JsonIgnore
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_at", nullable = false)
	@LastModifiedDate
	private Date updatedAt;

	@Builder.Default
	@JsonIgnore
	@Version
	private Integer version = 0;

	@JsonIgnore
	@Column(name = "created_by", updatable = false, columnDefinition = "VARCHAR(36)", length = 36)
	@CreatedBy
	private String createdBy;

	@JsonIgnore
	@Column(name = "updated_by", columnDefinition = "VARCHAR(36)", length = 36)
	@LastModifiedBy
	private String updatedBy;

}
