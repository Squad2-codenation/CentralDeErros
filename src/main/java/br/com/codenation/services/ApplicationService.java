package br.com.codenation.services;

import static br.com.codenation.specifications.GenericSpecificationBuilder.filterRecords;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.codenation.entities.Application;
import br.com.codenation.repositories.ApplicationRepository;

@Service
public class ApplicationService extends BaseService<ApplicationRepository, Application, UUID> {

    @Autowired
    public ApplicationService(ApplicationRepository repository) {
        super(repository);
    }

    public List<Application> findWithFilters(Map<Class<?>, Class<?>> params) {
        return repository.findAll(filterRecords(params));
    }
}
