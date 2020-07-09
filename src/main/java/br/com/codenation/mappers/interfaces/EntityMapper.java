package br.com.codenation.mappers.interfaces;

import br.com.codenation.entities.interfaces.BaseEntity;

public interface EntityMapper<E extends BaseEntity, DTO> {
	
	DTO toDTO(E source);

	E toEntity(DTO source);
}
