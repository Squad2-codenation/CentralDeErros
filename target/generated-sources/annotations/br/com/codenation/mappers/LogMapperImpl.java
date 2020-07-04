package br.com.codenation.mappers;

import br.com.codenation.dtos.LogDTO;
import br.com.codenation.entities.Application;
import br.com.codenation.entities.Log;
import br.com.codenation.entities.Log.LogBuilder;
import br.com.codenation.entities.User;
import br.com.codenation.enums.EnvironmentEnum;
import br.com.codenation.enums.LevelEnum;
import br.com.codenation.services.ApplicationService;
import br.com.codenation.services.UserService;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-04T18:09:36-0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 1.8.0_191 (Oracle Corporation)"
)
@Component
public class LogMapperImpl implements LogMapper {

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private UserService userService;

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
    public List<LogDTO> toDTOs(List<Log> sources) {
        if ( sources == null ) {
            return null;
        }

        List<LogDTO> list = new ArrayList<LogDTO>( sources.size() );
        for ( Log log : sources ) {
            list.add( toDTO( log ) );
        }

        return list;
    }

    @Override
    public Log toEntity(LogDTO source) {
        if ( source == null ) {
            return null;
        }

        LogBuilder log = Log.builder();

        log.application( applicationService.findById( source.getApplicationId() ) );
        log.user( userService.findById( source.getUserId() ) );
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

    @Override
    public List<Log> toEntities(List<LogDTO> sources) {
        if ( sources == null ) {
            return null;
        }

        List<Log> list = new ArrayList<Log>( sources.size() );
        for ( LogDTO logDTO : sources ) {
            list.add( toEntity( logDTO ) );
        }

        return list;
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
}
