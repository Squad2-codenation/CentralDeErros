package br.com.codenation.controllers;

import br.com.codenation.dtos.UserDTO;
import br.com.codenation.entities.User;
import br.com.codenation.mappers.UserMapper;
import br.com.codenation.repositories.UserRepository;
import br.com.codenation.services.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@Api(value = "Responsável pelo controle de usuários da aplicação.")
public class UserController extends BaseController<UserService, UserMapper, UserRepository, User, UserDTO, UUID> {

    @Autowired
    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }
}
