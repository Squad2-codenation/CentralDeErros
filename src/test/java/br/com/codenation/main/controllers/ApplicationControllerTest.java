package br.com.codenation.main.controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
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
import br.com.codenation.services.ApplicationService;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
public class ApplicationControllerTest {
	
	public static final String APP_NAME = "127.0.0.1";
	
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private ApplicationService applicationService;
	
	private Gson gson = new Gson();
	
	@Test
	@Transactional
	public void shouldReturnEveryApplication() throws Exception {
		Application application1 = createApplication(APP_NAME);

		Application application2 = createApplication("189.90.2.100");

		ResultActions perform = mvc.perform(get("/application?orderBy=name").contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andExpect(jsonPath("$.content", hasSize(2)));

		perform.andExpect(jsonPath("$.content[0].id", is(application1.getId().toString())));
		perform.andExpect(jsonPath("$.content[0].name", is(application1.getName())));
		perform.andExpect(jsonPath("$.content[0].token", is(application1.getToken())));

		perform.andExpect(jsonPath("$.content[1].id", is(application2.getId().toString())));
		perform.andExpect(jsonPath("$.content[1].name", is(application2.getName())));
		perform.andExpect(jsonPath("$.content[1].token", is(application2.getToken())));
	}
	
	@Test 
	@Transactional 
	public void shouldReturnNoApplications() throws Exception {
		mvc.perform(get("/application")
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.content", hasSize(0)));
	}
	
	@Test
	@Transactional
	public void shouldReturnAnApplicationWithTheSameId() throws Exception {
		Application application = createApplication(APP_NAME);
		
		ResultActions perform = mvc.perform(get("/application/"+application.getId().toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk());
		
		perform.andExpect(jsonPath("$.id", is(application.getId().toString())));
		perform.andExpect(jsonPath("$.name", is(application.getName())));
		perform.andExpect(jsonPath("$.token", is(application.getToken())));
		
	}
	
	@Test
	@Transactional
	public void shouldNotReturnAnyApplicationsBecauseOfInvalidId() throws Exception {
		Application application = createApplication(APP_NAME);
		
		ResultActions perform = mvc.perform(get("/application/" + UUID.randomUUID().toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is2xxSuccessful())
				.andExpect(jsonPath("$").doesNotExist());
	}
	
	@Test
	@Transactional 
	public void shouldBeSuccessfulWhenCreatingANewApplication() throws Exception {
		Application application = Application.builder()
				.id(UUID.randomUUID())
				.name(APP_NAME)
				.token(String.valueOf(Math.random()))
				.build();
		
		String jsonString = gson.toJson(application);
		
		mvc.perform(post("/application")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonString))
				.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@Transactional
	public void shouldReturnAnErrorWhenCreatingApplicationWithWrongIdType() throws Exception {
		String application = "{'id': 1, 'name':'"+APP_NAME+"'}";
		
		ResultActions perform = mvc.perform(post("/application")
				.content(application))
				.andExpect(status().is4xxClientError());
	}
	
	@Test
	@Transactional
	public void shouldBeSuccesfulWhenUpdatingAnApplication() throws Exception {
		Application application = Application.builder()
				.id(UUID.randomUUID())
				.name(APP_NAME)
				.token(String.valueOf(Math.random()))
				.build();
		
		String jsonString = gson.toJson(application);
		
		ResultActions perform = mvc.perform(post("/application")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(jsonString))
				.andExpect(status().is2xxSuccessful());
		
		application.setName("189.70.0.1");
		
		String jsonStringUpdate = gson.toJson(application);
		
		ResultActions resultUpdate = mvc.perform(put("/application/" + application.getId().toString())
				.content(jsonStringUpdate)
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is2xxSuccessful());
	}
	
	@Test
	@Transactional
	public void application_shouldBeSuccessfulWhenDeletingARecord() throws Exception {
		Application application1 = createApplication(APP_NAME);
		Application application2 = createApplication("189.90.10.111");
		
		List<Application> applicationsSaved = applicationService.findAll();
		
		assertThat(applicationsSaved, hasSize(2));
		
		ResultActions result = mvc.perform(delete("/application/" + application1.getId().toString())
				.contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().is2xxSuccessful());
		
		List<Application> applicationsAfterDeleting = applicationService.findAll();
		
		assertThat(applicationsAfterDeleting, hasSize(1));
	}
	
	private Application createApplication(String name) {
		Application application = Application.builder()
				.name(name)
				.token(String.valueOf(new Random().nextInt()))
				.build();
		return applicationService.save(application);
	}
 }
