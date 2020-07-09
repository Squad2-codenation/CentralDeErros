package br.com.codenation.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import br.com.codenation.dtos.UserDTO;
import br.com.codenation.entities.User;
import br.com.codenation.mappers.interfaces.EncodedMapping;
import br.com.codenation.mappers.interfaces.EntityMapper;

@Mapper(componentModel = "spring", uses = {PasswordEncoderMapper.class})
public interface UserMapper extends EntityMapper<User, UserDTO> {

	@Mapping(source = "createdAt", target = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
	@Mapping(source = "updatedAt", target = "updatedAt", dateFormat = "yyyy-MM-dd HH:mm")
	UserDTO toDTO(User user);

	@Mapping(target = "token", source = "token",
			defaultExpression = "java(br.com.codenation.utils.TokenUtil.tokenGenerator(source.getName(), source.getEmail()))")
	@Mapping(source = "password", target = "password", qualifiedBy = EncodedMapping.class)
	User toEntity(UserDTO source);
}
