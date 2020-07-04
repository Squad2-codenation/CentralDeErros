package br.com.codenation.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.codenation.dtos.LogDTO;
import br.com.codenation.entities.Log;
import br.com.codenation.mappers.LogMapper;
import br.com.codenation.repositories.LogRepository;
import br.com.codenation.services.LogService;

@RestController
@RequestMapping("/log")
public class LogController extends BaseController<LogService, LogMapper, LogRepository, Log, LogDTO, UUID> {

	@Autowired
	public LogController(LogService service, LogMapper mapper) {
		super(service, mapper);
	}

	@GetMapping("/filter")
	@ResponseStatus(HttpStatus.OK)
	public List<Log> listFilters(@RequestParam(required = false) Map<Class<?>, Class<?>> params,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "size", required = false, defaultValue = "5") int size,
			@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(direction), orderBy);
		return service.findWithFilters(params, pageRequest);
	}
}
