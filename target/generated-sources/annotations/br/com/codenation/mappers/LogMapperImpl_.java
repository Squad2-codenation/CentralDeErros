package br.com.codenation.mappers;

import br.com.codenation.dtos.LogDTO;
import br.com.codenation.entities.Application;
import br.com.codenation.entities.Application.ApplicationBuilder;
import br.com.codenation.entities.Log;
import br.com.codenation.entities.Log.LogBuilder;
import br.com.codenation.entities.User;
import br.com.codenation.entities.User.UserBuilder;
import br.com.codenation.enums.EnvironmentEnum;
import br.com.codenation.enums.LevelEnum;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-08T20:41:19-0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.7 (Oracle Corporation)"
)
@Component
@Qualifier("delegate")
public class LogMapperImpl_ implements LogMapper {

    @Override
    public LogDTO toDTO(Log source) {
        if ( source == null ) {
            return null;
        }

        LogDTO logDTO = new LogDTO();

        if ( source.getCreatedAt() != null ) {
            logDTO.setCreatedAt( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ).format( source.getCreatedAt() ) );
        }
        logDTO.setApplicationId( sourceApplicationId( source ) );
        logDTO.setUserName( sourceUserName( source ) );
        logDTO.setUserId( sourceUserId( source ) );
        logDTO.setApplicationName( sourceApplicationName( source ) );
        if ( source.getUpdatedAt() != null ) {
            logDTO.setUpdatedAt( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ).format( source.getUpdatedAt() ) );
        }
        logDTO.setId( source.getId() );
        logDTO.setTitle( source.getTitle() );
        logDTO.setDetails( source.getDetails() );
        logDTO.setArchived( source.getArchived() );
        if ( source.getLevel() != null ) {
            logDTO.setLevel( source.getLevel().name() );
        }
        logDTO.setEvents( source.getEvents() );
        if ( source.getEnvironment() != null ) {
            logDTO.setEnvironment( source.getEnvironment().name() );
        }

        return logDTO;
    }

    @Override
    public Log toEntity(LogDTO source) {
        if ( source == null ) {
            return null;
        }

        LogBuilder log = Log.builder();

        log.user( logDTOToUser( source ) );
        log.application( logDTOToApplication( source ) );
        log.id( source.getId() );
        log.title( source.getTitle() );
        log.details( source.getDetails() );
        log.archived( source.getArchived() );
        if ( source.getLevel() != null ) {
            log.level( Enum.valueOf( LevelEnum.class, source.getLevel() ) );
        }
        if ( source.getEnvironment() != null ) {
            log.environment( Enum.valueOf( EnvironmentEnum.class, source.getEnvironment() ) );
        }
        log.events( source.getEvents() );

        return log.build();
    }

    private UUID sourceApplicationId(Log log) {
        if ( log == null ) {
            return null;
        }
        Application application = log.getApplication();
        if ( application == null ) {
            return null;
        }
        UUID id = application.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String sourceUserName(Log log) {
        if ( log == null ) {
            return null;
        }
        User user = log.getUser();
        if ( user == null ) {
            return null;
        }
        String name = user.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private UUID sourceUserId(Log log) {
        if ( log == null ) {
            return null;
        }
        User user = log.getUser();
        if ( user == null ) {
            return null;
        }
        UUID id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String sourceApplicationName(Log log) {
        if ( log == null ) {
            return null;
        }
        Application application = log.getApplication();
        if ( application == null ) {
            return null;
        }
        String name = application.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected User logDTOToUser(LogDTO logDTO) {
        if ( logDTO == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.id( logDTO.getUserId() );

        return user.build();
    }

    protected Application logDTOToApplication(LogDTO logDTO) {
        if ( logDTO == null ) {
            return null;
        }

        ApplicationBuilder application = Application.builder();

        application.id( logDTO.getApplicationId() );

        return application.build();
    }
}
