package br.com.codenation.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
	public Page<LogDTO> listFilters(@RequestParam(required = false) Map<Class<?>, Class<?>> params,
									@RequestParam(value = "page", required = false, defaultValue = "0") int page,
									@RequestParam(value = "size", required = false, defaultValue = "5") int size,
									@RequestParam(value = "orderBy", defaultValue = "id") String orderBy,
									@RequestParam(value = "direction", defaultValue = "asc") String direction) {
		PageRequest pageRequest = PageRequest.of(page, size, Direction.fromString(direction), orderBy);
		return service.findWithFilters(params, pageRequest).map(e -> mapper.toDTO(e));
	}

	@PostMapping("/{id}/archive")
	@ApiOperation(value = "Archive the specified log")
	public LogDTO archive(@PathVariable UUID id) {
		return mapper.toDTO(service.updateArchive(id,true));
	}

	@PostMapping("/{id}/unarchive")
	@ApiOperation(value = "Unarchive the specified log")
	public LogDTO unarchive(@PathVariable UUID id) {
		return mapper.toDTO(service.updateArchive(id, false));
	}

	@PostMapping("/archive")
	@ApiOperation(value = "Archive logs in batch")
	public List<LogDTO> archiveInBatch(@RequestBody List<UUID> ids) {
		return service.updateArchiveInBatch(ids, true).stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}

	@PostMapping("/unarchive")
	@ApiOperation(value = "Unarchive logs in batch")
	public List<LogDTO> unarchiveInBatch(@RequestBody List<UUID> ids) {
		return service.updateArchiveInBatch(ids, true).stream()
				.map(mapper::toDTO)
				.collect(Collectors.toList());
	}
}
