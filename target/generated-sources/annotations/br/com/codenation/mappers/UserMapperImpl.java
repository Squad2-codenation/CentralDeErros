package br.com.codenation.mappers;

import br.com.codenation.dtos.UserDTO;
import br.com.codenation.entities.User;
import br.com.codenation.entities.User.UserBuilder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-05T16:32:11-0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.7 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        UserDTO userDTO = new UserDTO();

        if ( user.getCreatedAt() != null ) {
            userDTO.setCreatedAt( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ).format( user.getCreatedAt() ) );
        }
        if ( user.getUpdatedAt() != null ) {
            userDTO.setUpdatedAt( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ).format( user.getUpdatedAt() ) );
        }
        userDTO.setId( user.getId() );
        userDTO.setName( user.getName() );
        userDTO.setEmail( user.getEmail() );
        userDTO.setToken( user.getToken() );
        userDTO.setActive( user.getActive() );

        return userDTO;
    }

    @Override
    public List<UserDTO> toDTOs(List<User> sources) {
        if ( sources == null ) {
            return null;
        }

        List<UserDTO> list = new ArrayList<UserDTO>( sources.size() );
        for ( User user : sources ) {
            list.add( toDTO( user ) );
        }

        return list;
    }

    @Override
    public User toEntity(UserDTO source) {
        if ( source == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.id( source.getId() );
        user.name( source.getName() );
        user.email( source.getEmail() );
        user.token( source.getToken() );
        user.active( source.getActive() );

        return user.build();
    }

    @Override
    public List<User> toEntities(List<UserDTO> sources) {
        if ( sources == null ) {
            return null;
        }

        List<User> list = new ArrayList<User>( sources.size() );
        for ( UserDTO userDTO : sources ) {
            list.add( toEntity( userDTO ) );
        }

        return list;
    }
}
