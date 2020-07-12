package br.com.codenation.main.controllers;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import br.com.codenation.entities.User;
import br.com.codenation.services.UserService;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    private Gson gson = new Gson();

    @Test
    @Transactional
    public void user_shouldReturnAllUsers() throws Exception{
        User user1 = createUser("Joao Arnaldo de Freitas", "jão@joao.com.br-joao", "jarnaldo@teste.com", true);

        User user2 = createUser("Harry Potter", "sapoChocolate-teste2", "reuri@poti.com", true);

        ResultActions perform = mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        perform.andExpect(jsonPath("$[0].id", is(user1.getId().toString())));
        perform.andExpect(jsonPath("$[0].name", is(user1.getName())));
        perform.andExpect(jsonPath("$[0].token", is(user1.getToken())));
        perform.andExpect(jsonPath("$[0].email", is(user1.getEmail())));
        perform.andExpect(jsonPath("$[0].active", is(user1.getActive())));

        perform.andExpect(jsonPath("$[1].id", is(user2.getId().toString())));
        perform.andExpect(jsonPath("$[1].name", is(user2.getName())));
        perform.andExpect(jsonPath("$[1].token", is(user2.getToken())));
        perform.andExpect(jsonPath("$[1].email", is(user2.getEmail())));
        perform.andExpect(jsonPath("$[1].active", is(user2.getActive())));
    }
  
    @Test
    @Transactional
    public void user_shouldNotReturnAny() throws Exception{
        mvc.perform(get("/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    @Transactional
    public void user_shoulReturnOneById() throws Exception{
        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Severus Snape")
                .password("teste@teste")
                .token(String.valueOf((Math.random())))
                .email("severus@teste.com")
                .active(true)
                .build();

        ResultActions perform = mvc.perform(get("/user/" + user.getId().toString())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
        }

    @Test
    @Transactional
    public void user_shouldNotReturnAnyBecauseOfInvalidId() throws Exception {
        User user = createUser("Felipe Carneiro", "com.br-felipe", "felipec@teste.com", true);

        ResultActions perform = mvc.perform(get("/user/" + UUID.randomUUID())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @Transactional
    public void shouldBeSuccessfulWhenCreatingANewUser() throws Exception {
         User user = User.builder()
                .id(UUID.randomUUID())
                .name("Neville Longbottom")
                .password("teste@plantas")
                .token(String.valueOf((Math.random())))
                .email("neville@teste.com")
                .active(true)
                .build();

        String jsonString = gson.toJson(user);

        mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonString))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Transactional
    public void shouldReturnAnErrorWhenCreatingUserWithWrongIdType() throws Exception {
        String user = "{'id': 1, 'name':'Ron Weasley', 'password': 'teste123', 'email': 'teste@ron.com.uk', true}";

        ResultActions perform = mvc.perform(post("/user")
                .content(user))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Transactional
    public void shouldBeSuccesfulWhenUpdatingAnUser() throws Exception {
        Random rand = new Random();

        User user = User.builder()
                .id(UUID.randomUUID())
                .name("Hermione Jean Granger")
                .password("teste@hogwarts")
                .token(String.valueOf((rand.nextInt())))
                .email("hermione@teste.com")
                .active(true)
                .build();
        String jsonString = gson.toJson(user);

        ResultActions perform = mvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonString))
                .andExpect(status().is2xxSuccessful());

        user.setName("Hermione Jean Weasley");

        String jsonStringUpdate = gson.toJson(user);

        ResultActions resultUpdate = mvc.perform(put("/user/" + user.getId().toString())
                .content(jsonStringUpdate)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Transactional
    public void application_shouldBeSuccessfulWhenDeletingARecord() throws Exception {
        User user = createUser("Felipe Carneiro", "com.br-felipe", "felipec@teste.com", true);

        List<User> userSaved = userService.findAll();

        assertThat(userSaved, hasSize(1));

        ResultActions result = mvc.perform(delete("/user/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().is2xxSuccessful());

        List<User> userAfterDeleting = userService.findAll();

        assertThat(userAfterDeleting, hasSize(0));
    }

    private User createUser(String name, String password, String email, Boolean active){
        Random rand = new Random();
        User user = User.builder()
                .id(UUID.randomUUID())
                .name(name)
                .password(password)
                .token(String.valueOf((rand.nextInt())))
                .email(email)
                .active(active)
                .build();

        return userService.save(user);
    }
}
