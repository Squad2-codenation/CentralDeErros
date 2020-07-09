package br.com.codenation.main.controllers;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import br.com.codenation.entities.Application;
import br.com.codenation.entities.Log;
import br.com.codenation.enums.EnvironmentEnum;
import br.com.codenation.enums.LevelEnum;
import br.com.codenation.repositories.ApplicationRepository;
import br.com.codenation.repositories.UserRepository;
import br.com.codenation.services.ApplicationService;
import br.com.codenation.services.LogService;
import br.com.codenation.services.UserService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class LogControllerTest {
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired 
	private LogService logService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationService applicationService;

	private Gson gson = new Gson();
	
	@Test
	@Transactional
	public void log_shouldReturnAllRecords() throws Exception {
		Log log1 = createFirstLog();
		Log log2 = createSecondLog();

		ResultActions perform = mvc.perform(get("/log").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(2)));
		
		perform.andExpect(jsonPath("$[0].id", is(log1.getId().toString())));
		perform.andExpect(jsonPath("$[0].title", is(log1.getTitle())));
		perform.andExpect(jsonPath("$[0].details", is(log1.getDetails())));

		perform.andExpect(jsonPath("$[1].id", is(log2.getId().toString())));
		perform.andExpect(jsonPath("$[1].title", is(log2.getTitle())));
		perform.andExpect(jsonPath("$[1].details", is(log2.getDetails())));
	}
	
	@Test 
	@Transactional 
	public void log_shouldReturnNothing() throws Exception {
		mvc.perform(get("/log")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}
	
	@Test
	@Transactional
	public void log_shouldReturnRecordWithTheSameId() throws Exception {
		Log log = createFirstLog();
		
		ResultActions perform = mvc.perform(get("/log/"+log.getId().toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
	}
	
	@Test
	@Transactional
	public void log_shouldNotReturnAnyRecordsBecauseOfInvalidId() throws Exception {
		Log log = createFirstLog();
		
		ResultActions perform = mvc.perform(get("/log/" + UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$").doesNotExist());
	}
	
	@Test
	@Transactional 
	public void log_shouldBeSuccessfulWhenCreatingANewRecord() throws Exception {
		Log log = Log.builder()
				.id(UUID.randomUUID())
				.title("acceleration.Service.AddCandidate: <forbidden>")
				.details("File \"/br/com/codenation/service/CandidateService.java\", line 83, in findAll")
				.application(createApplication())
				.environment(EnvironmentEnum.DEVELOPMENT)
				.level(LevelEnum.DEBUG)
				.user(createUser())
				.build();	
		
		String jsonString = gson.toJson(log);
		
		mvc.perform(post("/log")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonString))
				.andExpect(status().is2xxSuccessful());
	}
		
	@Test
	@Transactional
	public void log_shouldBeSuccesfulWhenUpdatingARecord() throws Exception {
		Log log = Log.builder()
				.id(UUID.randomUUID())
				.title("acceleration.Service.AddCandidate: <forbidden>")
				.details("File \"/br/com/codenation/service/CandidateService.java\", line 83, in findAll")
				.application(createApplication())
				.environment(EnvironmentEnum.DEVELOPMENT)
				.level(LevelEnum.DEBUG)
				.user(createUser())
				.build();
		
		String jsonString = gson.toJson(log);
		
		mvc.perform(post("/log")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonString))
				.andExpect(status().is2xxSuccessful());
		
		List<Log> logsSaved = logService.findAll();
		
		assertThat(logsSaved, hasSize(1));
		
		log.setEnvironment(EnvironmentEnum.PRODUCTION);
			
		String jsonStringUpdate = gson.toJson(log);
		
		ResultActions resultUpdate = mvc.perform(put("/log/" + log.getId().toString())
				.content(jsonStringUpdate)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@Transactional 
	public void log_shouldBeSuccessfulWhenDeletingARecord() throws Exception {
		Log log1 = createFirstLog();
		Log log2 = createSecondLog();
		
		List<Log> logsSaved = logService.findAll();
		
		assertThat(logsSaved, hasSize(2));
		
		ResultActions result = mvc.perform(delete("/log/" + log1.getId().toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is2xxSuccessful());
		
		List<Log> logsAfterDeleting = logService.findAll();
		
		assertThat(logsAfterDeleting, hasSize(1));
	}
	
	private Log createFirstLog() {
		Log log = Log.builder()
				.title("acceleration.Service.AddCandidate: <forbidden>")
				.details("File \"/br/com/codenation/service/CandidateService.java\", line 83, in findAll")
				.application(createApplication())
				.environment(EnvironmentEnum.DEVELOPMENT)
				.level(LevelEnum.DEBUG)
				.user(createUser())
				.id(UUID.randomUUID())
				.build();	
		
		return logService.save(log);
	}
	
	private Log createSecondLog() {
		Log log = Log.builder()
				.title("acceleration.Service.AddCompany: <forbidden>")
				.details("File \"/br/com/codenation/service/CompanyService.java\", line 83, in findAll")
				.application(createApplication())
				.environment(EnvironmentEnum.HOMOLOGATION)
				.level(LevelEnum.ERROR)
				.user(createUser())
				.id(UUID.randomUUID())
				.build();		
		
		return logService.save(log);
	}
	
	private Application createApplication() {
		Application application = Application.builder()
				.name("127.0.0.1")
				.id(UUID.randomUUID())
				.token(String.valueOf(new Random().nextInt()))
				.build();
		
		return applicationService.save(application);
	}
	
	private br.com.codenation.entities.User createUser() {
		br.com.codenation.entities.User user = br.com.codenation.entities.User.builder()
				.name("Alex Turner")
				.email("alexturner@am.com.br")
				.password("123456")
				.id(UUID.randomUUID())
				.build();
		
		return userService.save(user);
	}
}
