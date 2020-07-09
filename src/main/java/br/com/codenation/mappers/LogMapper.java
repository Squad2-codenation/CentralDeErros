package br.com.codenation.mappers;

import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.codenation.dtos.LogDTO;
import br.com.codenation.entities.Log;
import br.com.codenation.mappers.interfaces.EntityMapper;
import br.com.codenation.services.ApplicationService;
import br.com.codenation.services.UserService;

@DecoratedWith(LogMapperDecorator.class)
@Mapper(componentModel = "spring", uses = { ApplicationService.class, UserService.class})
public interface LogMapper extends EntityMapper<Log, LogDTO> {

	@Mapping(source = "application.id", target = "applicationId")
	@Mapping(source = "application.name", target = "applicationName")
	@Mapping(source = "user.id", target = "userId")
	@Mapping(source = "user.name", target = "userName")
	@Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
	@Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm")
	LogDTO toDTO(Log source);

	@Mapping(source = "applicationId", target = "application.id")
	@Mapping(source = "userId", target = "user.id")
	Log toEntity(LogDTO source);

}
