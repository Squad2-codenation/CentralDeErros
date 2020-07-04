package br.com.codenation.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.codenation.dtos.ApplicationDTO;
import br.com.codenation.entities.Application;
import br.com.codenation.mappers.ApplicationMapper;
import br.com.codenation.repositories.ApplicationRepository;
import br.com.codenation.services.ApplicationService;

@RestController
@RequestMapping("/application")
public class ApplicationController extends BaseController<ApplicationService, ApplicationMapper, ApplicationRepository, Application, ApplicationDTO, UUID> {

    @Autowired
    public ApplicationController(ApplicationService service, ApplicationMapper mapper) {
        super(service, mapper);
    }
}
