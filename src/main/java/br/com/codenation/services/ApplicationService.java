package br.com.codenation.services;

import br.com.codenation.entities.Application;
import br.com.codenation.repositories.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplicationService extends BaseService<ApplicationRepository, Application, UUID> {

    @Autowired
    public ApplicationService(ApplicationRepository repository) {
        super(repository);
    }


}
