package br.com.codenation.controllers;

import br.com.codenation.model.Application;
import br.com.codenation.model.User;
import br.com.codenation.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@Api(value = "Responsável pelo controle de usuários da aplicação.")
public class UserController extends AbstractController<User, UUID>{

    @Autowired
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        super(userService);
        this.userService = userService;
    }

    @GetMapping 
    @ResponseStatus(HttpStatus.OK)
    public List<User> listFilters(@RequestParam(required = false) Map<Class<?>, Class<?>> params) {
    	return userService.findWithFilters(params);
    }
}
