package br.com.codenation.mappers;

import br.com.codenation.dtos.UserDTO;
import br.com.codenation.entities.User;
import br.com.codenation.entities.User.UserBuilder;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-08T20:41:19-0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.7 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Autowired
    private PasswordEncoderMapper passwordEncoderMapper;

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
        userDTO.setPassword( user.getPassword() );

        return userDTO;
    }

    @Override
    public User toEntity(UserDTO source) {
        if ( source == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.password( passwordEncoderMapper.encode( source.getPassword() ) );
        if ( source.getToken() != null ) {
            user.token( source.getToken() );
        }
        else {
            user.token( br.com.codenation.utils.TokenUtil.tokenGenerator(source.getName(), source.getEmail()) );
        }
        user.id( source.getId() );
        user.name( source.getName() );
        user.email( source.getEmail() );
        user.active( source.getActive() );

        return user.build();
    }
}
