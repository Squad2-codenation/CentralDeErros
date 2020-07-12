package br.com.codenation.controllers;

import br.com.codenation.dtos.UserDTO;
import br.com.codenation.entities.User;
import br.com.codenation.mappers.UserMapper;
import br.com.codenation.repositories.UserRepository;
import br.com.codenation.services.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
@Api(value = "Responsável pelo controle de usuários da aplicação.")
public class UserController extends BaseController<UserService, UserMapper, UserRepository, User, UserDTO, UUID> {

    @Autowired
    public UserController(UserService service, UserMapper mapper) {
        super(service, mapper);
    }
    
    @PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Adiciona um novo registro no banco")
	public UserDTO create(@Valid @RequestBody UserDTO dto) {
		return mapper.toDTO(service.save(mapper.toEntity(dto)));
	}

    @PostMapping("/{id}/deactivate")
    @ApiOperation(value = "Deactivate the specified user")
    public void deactivate(@PathVariable UUID id) {
        service.updateActive(id, false);
    }

    @PostMapping("/{id}/activate")
    @ApiOperation(value = "Activate the specified user")
    public void activate(@PathVariable UUID id) {
        service.updateActive(id, true);
    }
}
