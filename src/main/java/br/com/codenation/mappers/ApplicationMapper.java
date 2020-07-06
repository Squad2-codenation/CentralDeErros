package br.com.codenation.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.codenation.dtos.ApplicationDTO;
import br.com.codenation.entities.Application;
import br.com.codenation.mappers.interfaces.EntityMapper;

@Mapper(componentModel = "spring")
public interface ApplicationMapper extends EntityMapper<Application, ApplicationDTO> {

	@Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
	@Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm")
	ApplicationDTO toDTO(Application source);

	List<ApplicationDTO> toDTOs(List<Application> sources);

	@Mapping(target = "token", source = "token",
			defaultExpression = "java(br.com.codenation.utils.TokenUtil.tokenGenerator(source.getName()))")
	Application toEntity(ApplicationDTO source);

	List<Application> toEntities(List<ApplicationDTO> sources);

}
