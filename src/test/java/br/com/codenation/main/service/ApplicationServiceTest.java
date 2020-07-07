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
	public void whenFindAllReturns() {
		createApplication(APP_NAME);
		createApplication("189.29.10.2");
		
		List<Application> result = applicationRepository.findAll();
		
		assertThat(result, hasSize(2));
	}
	
	@Test 
	@Transactional
	public void whenFindByIdReturns() {
		Application application = createApplication(APP_NAME);
		
		Optional<Application> result = applicationRepository.findById(application.getId());
		
		assertThat(result.isPresent(), is(Boolean.TRUE));
		assertThat(result.get().getName(), equalTo(APP_NAME));
	}
	
	@Test
	@Transactional
	public void whenApplicationWithGivenIdDoesNotExist() {
		Optional<Application> result = applicationRepository.findById(UUID.randomUUID());
		
		assertThat(result.isPresent(), is(Boolean.FALSE));
	}
	
	@Test
	@Transactional
	public void whenThereAreNoApplications() {
		List<Application> result = applicationRepository.findAll();
		
		assertThat(result, hasSize(0));
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
