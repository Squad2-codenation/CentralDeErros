package br.com.codenation.service;

import br.com.codenation.model.Application;
import br.com.codenation.model.Error;
import br.com.codenation.repositories.ErrorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static br.com.codenation.specification.GenericSpecificationBuilder.filterRecords;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ErrorService extends AbstractService<Error, UUID> {

    private ErrorRepository errorRepository;

    @Autowired
    public ErrorService(ErrorRepository errorRepository){
        super(errorRepository);
        this.errorRepository = errorRepository;
    }

    public List<Error> findWithFilters(Map<Class<?>, Class<?>> params) {
    	return errorRepository.findAll(filterRecords(params));
    }
}
