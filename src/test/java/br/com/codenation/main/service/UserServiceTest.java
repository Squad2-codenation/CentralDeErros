package br.com.codenation.main.service;

import br.com.codenation.entities.User;
import br.com.codenation.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class UserServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void whenFindAllUsers(){
        User user1 = createUser("Joao Arnaldo de Freitas", "jão@joao.com.br-joao", "jarnaldo@teste.com", true);

        User user2 = createUser("Harry Potter", "sapoChocolate", "reuri@poti.com", true);

        List<User> result = userRepository.findAll();

        assertThat(result, hasSize(2));
    }

    @Test
    @Transactional
    public void whenFindUserById(){
        User user = createUser("Gisele Almeida Pereira", "gisele.com.br-pereira", "giselep@teste.com", true);

        Optional<User> result =userRepository.findById(user.getId());

        assertThat(result.isPresent(), is(Boolean.TRUE));
        assertThat(result.get().getName(), equalTo(user.getName()));
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

        return userRepository.save(user);
    }
}
