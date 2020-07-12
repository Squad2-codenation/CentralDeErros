package br.com.codenation.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.codenation.entities.interfaces.BaseEntity;
import br.com.codenation.mappers.interfaces.EntityMapper;
import br.com.codenation.services.BaseService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BaseController<S extends BaseService<R, E, ID>, M extends EntityMapper<E, D>, R extends JpaRepository<E, ID>, E extends BaseEntity<ID>, D, ID> {

	protected S service;
	protected M mapper;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List all entities")
	public List<D> listAll() {
		return service.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@GetMapping("/filteredList")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List all entities based on the entity filter")
	public List<D> filteredList() {
		return service.filteredList().stream().map(mapper::toDTO).collect(Collectors.toList());
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get the entity by id")
	public D getById(@PathVariable ID id) {
		return mapper.toDTO(service.findById(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a new record")
	public D create(@RequestBody D dto) {
		return mapper.toDTO(service.save(mapper.toEntity(dto)));
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Delete a record by id")
	public void delete(@PathVariable ID id) {
		service.delete(id);
	}

	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Update a record in the database")
	public D update(@PathVariable ID id, @RequestBody D dto) {
		E model = mapper.toEntity(dto);
		model.setId(id);
		return mapper.toDTO(service.update(model));
	}

}
