package br.com.codenation.services;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.querydsl.core.types.dsl.BooleanExpression;

import br.com.codenation.entities.Log;
import br.com.codenation.querydsl.LogPredicatesBuilder;
import br.com.codenation.repositories.LogRepository;

@Service
public class LogService extends BaseService<LogRepository, Log, UUID> {
	@Autowired
	public LogService(LogRepository repository) {
		super(repository);
	}
	
	public Iterable<Log> findWithFilters(String search, Pageable pageable) {
		LogPredicatesBuilder builder = new LogPredicatesBuilder();
		
		if (search != null) {
			Pattern pattern = Pattern.compile("(\\w+)(:|>|<)((\\w+[.]?)+\\w+),");
			Matcher matcher = pattern.matcher(search + ",");
			while (matcher.find()) {
				builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
			}
		}	
			
		BooleanExpression exp = builder.build();
		
		return repository.findAll(exp, pageable);
	}

	public Long countEvents(UUID logId){
		return repository.countEvents(logId);
	}
}
