package br.com.codenation.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;

import br.com.codenation.entities.interfaces.BaseEntity;
import br.com.codenation.enums.EnvironmentEnum;
import br.com.codenation.enums.LevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "log")
@Table(indexes = {@Index(name = "idx_title", columnList = "title"),
		@Index(name = "idx_archived", columnList = "archived"),
		@Index(name = "idx_level", columnList = "level"),
		@Index(name = "idx_environment", columnList = "environment")})
public class Log extends AbstractAuditingEntity implements BaseEntity<UUID> {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	private UUID id;

	@NotNull
	private String title;

	@NotNull
	private String details;

	@NotNull
	@ManyToOne
	private Application application;

	@Column(columnDefinition = "boolean default false")
	private Boolean archived = false;

	@ManyToOne
	private User user;

	@NotNull
	@Enumerated(EnumType.STRING)
	private LevelEnum level;

	@NotNull
	@Enumerated(EnumType.STRING)
	private EnvironmentEnum environment;

	@Formula("(SELECT count(1) " +
			"FROM log l "+
			"JOIN application a " +
			"ON l.application_id = a.id " +
			"WHERE l.archived = false " +
			"AND l.level = level " +
			"AND l.environment = environment " +
			"AND l.title = title)")
	private Long events;

}