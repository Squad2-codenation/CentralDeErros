package br.com.codenation.mappers;

import br.com.codenation.dtos.LogDTO;
import br.com.codenation.entities.Log;
import br.com.codenation.services.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class LogMapperDecorator implements LogMapper {

    @Autowired
    @Qualifier("delegate")
    private LogMapper delegate;

    @Autowired
    private LogService logService;

    @Override
    public LogDTO toDTO(Log source) {
        if(source == null){
            return null;
        }
        LogDTO logDTO = delegate.toDTO(source);
        logDTO.setEvents(logService.countEvents(source.getId()));
        return logDTO;
    }
}
