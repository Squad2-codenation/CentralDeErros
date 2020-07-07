package br.com.codenation.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.codenation.entities.Application;
import br.com.codenation.repositories.ApplicationRepository;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
public class ApplicationServiceTest {
	
	public static final String APP_NAME = "127.0.0.1";
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Test 
	@Transactional
	public void application_whenFindAllShouldBeSuccessful() {
		createApplication(APP_NAME);
		createApplication("189.29.10.2");
		
		List<Application> result = applicationRepository.findAll();
		
		assertThat(result, hasSize(2));
	}
	
	@Test
	@Transactional
	public void application_whenFindAllReturnsNothing() {
		List<Application> result = applicationRepository.findAll();
		
		assertThat(result, hasSize(0));
	}
	
	@Test 
	@Transactional
	public void application_whenFindByIdShouldBeSuccessful() {
		Application application = createApplication(APP_NAME);
		
		Optional<Application> result = applicationRepository.findById(application.getId());
		
		assertThat(result.isPresent(), is(Boolean.TRUE));
		assertThat(result.get().getName(), equalTo(APP_NAME));
	}
	
	@Test
	@Transactional
	public void application_whenFindByIdReturnsNothing() {
		Optional<Application> result = applicationRepository.findById(UUID.randomUUID());
		
		assertThat(result.isPresent(), is(Boolean.FALSE));
	}
	
	@Test
	@Transactional
	public void application_whenSavingShouldBeSuccessful() {
		Application application = Application.builder()
				.name(APP_NAME)
				.id(UUID.randomUUID())
				.token(String.valueOf(new Random().nextInt()))
				.build();
		
		Application result = applicationRepository.save(application);
		
		assertThat(result.getName(), equalTo(application.getName()));
	}
	
	@Test
	@Transactional
	public void application_whenDeleteShouldBeSuccessful() {
		Application application = createApplication(APP_NAME);
		Application application2 = createApplication("189.90.2.80");
		
		List<Application> result = applicationRepository.findAll();
		
		assertThat(result, hasSize(2));
		
		applicationRepository.delete(application);
		
		List<Application> result2 = applicationRepository.findAll();
		
		assertThat(result2, hasSize(1));
	}
	
	private Application createApplication(String name) {
		Application application = Application.builder()
				.name(name)
				.id(UUID.randomUUID())
				.token(String.valueOf(new Random().nextInt()))
				.build();
		return applicationRepository.save(application);
	}
}
