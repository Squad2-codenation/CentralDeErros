package br.com.codenation.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import br.com.codenation.entities.interfaces.BaseEntity;
import br.com.codenation.mappers.interfaces.EntityMapper;
import br.com.codenation.services.BaseService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

import javax.validation.Valid;

@AllArgsConstructor
public abstract class BaseController<S extends BaseService<R, E, ID>, M extends EntityMapper<E, D>, R extends JpaRepository<E, ID>, E extends BaseEntity<ID>, D, ID> {

	protected S service;
	protected M mapper;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List all entities")
	public Page<D> listAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
						   @RequestParam(value = "size", required = false, defaultValue = "5") int size,
						   @RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
						   @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), orderBy);
		return service.findAll(pageRequest).map(e -> mapper.toDTO(e));
	}

	@GetMapping("/filteredList")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "List all entities based on the entity filter")
	public Page<D> filteredList(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
								@RequestParam(value = "size", required = false, defaultValue = "5") int size,
								@RequestParam(value = "orderBy", required = false, defaultValue = "id") String orderBy,
								@RequestParam(value = "direction", required = false, defaultValue = "asc") String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.fromString(direction), orderBy);
		return service.filteredList(pageRequest).map(e -> mapper.toDTO(e));
	}

	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "Get the entity by id")
	public D getById(@Valid @PathVariable ID id) {
		return mapper.toDTO(service.findById(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create a new record")
	public D create(@Valid @RequestBody D dto) {
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
