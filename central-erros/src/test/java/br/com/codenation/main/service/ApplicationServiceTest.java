package br.com.codenation.main.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.codenation.model.Application;
import br.com.codenation.repositories.ApplicationRepository;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.*;

@SpringBootTest
public class ApplicationServiceTest {
	
	@Autowired
	private ApplicationRepository applicationRepository;
	
	@Test 
	@Transactional
	public void whenFindAllReturns() {
		Application application1 = createApplication("127.0.0.1");
				
		Application application2 = createApplication("189.29.10.2");
		
		List<Application> result = applicationRepository.findAll();
		
		assertThat(result, hasSize(2));
	}
	
	@Test 
	@Transactional
	public void whenFindByIdReturns() {
		Application application = createApplication("127.0.0.1");
		
		Optional<Application> result = applicationRepository.findById(application.getId());
		
		assertThat(result.isPresent(), is(Boolean.TRUE));
		assertThat(result.get().getName(), equalTo("127.0.0.1"));
	}
	
	private Application createApplication(String name) {
		Random rand = new Random();
		Application application = Application.builder()
				.name(name)
				.id(UUID.randomUUID())
				.token(String.valueOf(rand.nextInt()))
				.build();
		return applicationRepository.save(application);
	}
}
