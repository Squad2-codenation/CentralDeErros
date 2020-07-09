package br.com.codenation.main.service;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import br.com.codenation.entities.Application;
import br.com.codenation.entities.Log;
import br.com.codenation.enums.EnvironmentEnum;
import br.com.codenation.enums.LevelEnum;
import br.com.codenation.repositories.ApplicationRepository;
import br.com.codenation.repositories.LogRepository;
import br.com.codenation.repositories.UserRepository;

@SpringBootTest
public class LogServiceTest {
	
	@Autowired
	private LogRepository logRepository;
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	@Transactional
	public void log_whenFindAllShouldBeSuccessful() {
		createFirstLog();
		createSecondLog();
		
		List<Log> result = logRepository.findAll();
		
		assertThat(result, hasSize(2));
	}
	
	@Test
	@Transactional
	public void log_whenFindAllReturnsNothing() {
		List<Log> result = logRepository.findAll();
		
		assertThat(result, hasSize(0));
	}
	
	@Test
	@Transactional
	public void log_whenFindByIdShouldBeSuccessful() {
		Log log = createFirstLog();
		
		Optional<Log> result = logRepository.findById(log.getId());
		
		assertThat(result.isPresent(), is(Boolean.TRUE));
		assertThat(result.get().getTitle(), equalTo(log.getTitle()));
	}
	
	@Test
	@Transactional
	public void log_whenFindByIdReturnsNothing() {
		Optional<Log> result = logRepository.findById(UUID.randomUUID());
		
		assertThat(result.isPresent(), is(Boolean.FALSE));
	}
	
	@Test 
	@Transactional
	public void log_whenSavingShouldBeSuccessful() {
		Log log = Log.builder()
				.title("acceleration.Service.AddCandidate: <forbidden>")
				.details("File \"/br/com/codenation/service/CandidateService.java\", line 83, in findAll")
				.application(createApplication())
				.environment(EnvironmentEnum.DEVELOPMENT)
				.level(LevelEnum.DEBUG)
				.user(createUser())
				.id(UUID.randomUUID())
				.build();
		
		Log result = logRepository.save(log);
				
		assertThat(result.getTitle(), equalTo(log.getTitle()));
	}
	
	@Test
	@Transactional
	public void log_whenDeleteShouldBeSuccesful() {
		Log log = createFirstLog();
		Log log2 = createSecondLog();
		
		List<Log> result = logRepository.findAll();
		
		assertThat(result, hasSize(2));

		logRepository.delete(log);
		
		List<Log> result2 = logRepository.findAll();
		
		assertThat(result2, hasSize(1));
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
		
		return logRepository.save(log);
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
		
		return logRepository.save(log);
	}
	
	private Application createApplication() {
		Application application = Application.builder()
				.name("127.0.0.1")
				.id(UUID.randomUUID())
				.token(String.valueOf(new Random().nextInt()))
				.build();
		
		return applicationRepository.save(application);
	}
	
	private br.com.codenation.entities.User createUser() {
		br.com.codenation.entities.User user = br.com.codenation.entities.User.builder()
				.name("Alex Turner")
				.email("alexturner@am.com.br")
				.password("123456")
				.id(UUID.randomUUID())
				.build();
		
		return userRepository.save(user);
	}
}
