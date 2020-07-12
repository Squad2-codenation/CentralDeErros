package br.com.codenation.services;

import static br.com.codenation.specifications.ErrorSpecification.addFilters;

import java.util.List;
import java.util.Map;
import java.util.UUID;
	
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.codenation.entities.Log;
import br.com.codenation.repositories.LogRepository;

@Service
public class LogService extends BaseService<LogRepository, Log, UUID> {

	@Autowired
	public LogService(LogRepository repository) {
		super(repository);
	}

	public List<Log> findWithFilters(Map<Class<?>, Class<?>> params, Pageable pageable) {
		Page<Log> pageData = repository.findAll(addFilters(params), pageable);
		return pageData.getContent();
	}

	public Long countEvents(UUID logId){
		return repository.countEvents(logId);
	}

	@Override
	public List<Log> filteredList() {
		return repository.findAllNotArchived();
	}
}
