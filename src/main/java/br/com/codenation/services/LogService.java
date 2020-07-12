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

	public Page<Log> findWithFilters(Map<Class<?>, Class<?>> params, Pageable pageable) {
		return repository.findAll(addFilters(params), pageable);
	}

	public Long countEvents(UUID logId){
		return repository.countEvents(logId);
	}

	public Log updateArchive(UUID id, Boolean value){
		Log log = findById(id);
		if(log != null){
			log.setArchived(value);
			log = save(log);
		}

		return log;
	}

	public List<Log> updateArchiveInBatch(List<UUID> ids, Boolean value){
		List<Log> logsToArchive = findAllById(ids);
		for(Log log : logsToArchive){
			log.setArchived(value);
		}

		return repository.saveAll(logsToArchive);
	}

	@Override
	public Page<Log> filteredList(Pageable pageable) {
		return repository.findAllNotArchived(pageable);
	}
}
