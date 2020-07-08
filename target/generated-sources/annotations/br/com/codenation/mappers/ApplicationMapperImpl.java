package br.com.codenation.mappers;

import br.com.codenation.dtos.ApplicationDTO;
import br.com.codenation.entities.Application;
import br.com.codenation.entities.Application.ApplicationBuilder;
import java.time.format.DateTimeFormatter;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-08T20:41:19-0300",
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
    public Application toEntity(ApplicationDTO source) {
        if ( source == null ) {
            return null;
        }

        ApplicationBuilder application = Application.builder();

        if ( source.getToken() != null ) {
            application.token( source.getToken() );
        }
        else {
            application.token( br.com.codenation.utils.TokenUtil.tokenGenerator(source.getName()) );
        }
        application.id( source.getId() );
        application.name( source.getName() );

        return application.build();
    }
}
