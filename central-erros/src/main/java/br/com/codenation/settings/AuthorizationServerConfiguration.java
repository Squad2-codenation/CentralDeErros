package br.com.codenation.settings;

import br.com.codenation.login.LoggedUser;
import br.com.codenation.model.User;
import br.com.codenation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    protected void configure(AuthenticationManagerBuilder auth, UserRepository userRepository) throws Exception {
        auth.userDetailsService(email -> {
            return userRepository.findByEmail(email)
                    .map(LoggedUser::new)
                    .orElse(null);
        }).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(HttpMethod.GET, //
                "/", //
                "/webjars/**", //
                "/*.html", //
                "/favicon.ico", //
                "/**/*.html", //
                "/v2/api-docs", //
                "/configuration/ui", //
                "/swagger-resources/**", //
                "/configuration/**", //
                "/swagger-ui.html", //
                "/webjars/**", //
                "/**/*.css", //
                "/**/*.js"//
        );
    }

}
