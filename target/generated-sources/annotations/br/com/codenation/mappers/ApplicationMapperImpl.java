package br.com.codenation.mappers;

import br.com.codenation.dtos.ApplicationDTO;
import br.com.codenation.entities.Application;
import br.com.codenation.entities.Application.ApplicationBuilder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-04T16:39:20-0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.7 (Oracle Corporation)"
)
@Component
public class ApplicationMapperImpl implements ApplicationMapper {

    @Override
    public ApplicationDTO toDTO(Application source) {
        if ( source == null ) {
            return null;
        }

        ApplicationDTO applicationDTO = new ApplicationDTO();

        if ( source.getCreatedAt() != null ) {
            applicationDTO.setCreatedAt( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ).format( source.getCreatedAt() ) );
        }
        if ( source.getUpdatedAt() != null ) {
            applicationDTO.setUpdatedAt( DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm" ).format( source.getUpdatedAt() ) );
        }
        applicationDTO.setId( source.getId() );
        applicationDTO.setName( source.getName() );
        applicationDTO.setToken( source.getToken() );

        return applicationDTO;
    }

    @Override
    public List<ApplicationDTO> toDTOs(List<Application> sources) {
        if ( sources == null ) {
            return null;
        }

        List<ApplicationDTO> list = new ArrayList<ApplicationDTO>( sources.size() );
        for ( Application application : sources ) {
            list.add( toDTO( application ) );
        }

        return list;
    }

    @Override
    public Application toEntity(ApplicationDTO source) {
        if ( source == null ) {
            return null;
        }

        ApplicationBuilder application = Application.builder();

        application.id( source.getId() );
        application.name( source.getName() );
        application.token( source.getToken() );

        return application.build();
    }

    @Override
    public List<Application> toEntities(List<ApplicationDTO> sources) {
        if ( sources == null ) {
            return null;
        }

        List<Application> list = new ArrayList<Application>( sources.size() );
        for ( ApplicationDTO applicationDTO : sources ) {
            list.add( toEntity( applicationDTO ) );
        }

        return list;
    }
}
