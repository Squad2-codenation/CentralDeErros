package br.com.codenation.mappers;

import br.com.codenation.dtos.LogDTO;
import br.com.codenation.entities.Log;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2020-07-08T20:41:19-0300",
    comments = "version: 1.3.0.Final, compiler: javac, environment: Java 11.0.7 (Oracle Corporation)"
)
@Component
@Primary
public class LogMapperImpl extends LogMapperDecorator implements LogMapper {

    @Autowired
    @Qualifier("delegate")
    private LogMapper delegate;

    @Override
    public Log toEntity(LogDTO source)  {
        return delegate.toEntity( source );
    }
}
