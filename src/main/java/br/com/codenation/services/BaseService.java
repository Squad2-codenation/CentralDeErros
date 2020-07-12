package br.com.codenation.services;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.codenation.entities.interfaces.BaseEntity;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class BaseService<R extends JpaRepository<E, ID>, E extends BaseEntity, ID> {

	protected final R repository;

	public E save(E model) {
		return repository.save(model);
	}

	public List<E> saveAll(List<E> models) {
		return repository.saveAll(models);
	}

	public E update(E model) {
		return repository.save(model);
	}

	public E findById(ID id) {
		return repository.findById(id).orElse(null);

	}

	public List<E> findAll() {
		return repository.findAll();
	}

	public void delete(ID id) {
		repository.deleteById(id);

	}

	public List<E> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	public List<E> findAllById(Iterable<ID> ids) {
		return repository.findAllById(ids);
	}

	public List<E> saveAll(Iterable<E> models) {
		return repository.saveAll(models);
	}

	public void flush() {
		repository.flush();
	}

	public E saveAndFlush(E model) {
		return repository.saveAndFlush(model);
	}

	public void deleteInBatch(Iterable<E> models) {
		repository.deleteInBatch(models);
	}

	public void deleteAllInBatch() {
		repository.deleteAllInBatch();
	}

	public List<E> filteredList() {
		return repository.findAll();
	}

}
