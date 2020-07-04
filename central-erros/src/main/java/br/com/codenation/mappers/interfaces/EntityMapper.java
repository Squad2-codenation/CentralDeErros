package br.com.codenation.mappers.interfaces;

import java.util.List;

import br.com.codenation.entities.interfaces.BaseEntity;

public interface EntityMapper<E extends BaseEntity, DTO> {

	DTO toDTO(E source);

	List<DTO> toDTOs(List<E> sources);

	E toEntity(DTO source);

	List<E> toEntities(List<DTO> sources);
}
