package br.com.codenation.configurations;

import br.com.codenation.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import br.com.codenation.login.LoggedUser;
import br.com.codenation.repositories.UserRepository;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Autowired
	protected void configure(AuthenticationManagerBuilder auth, UserRepository userRepository) {
		try {
			auth.userDetailsService(email -> userRepository.findByEmail(email).map(LoggedUser::new).orElse(null))
					.passwordEncoder(passwordEncoder());
		} catch (Exception e) {
			throw new BadRequestException("Error during user authentication");
		}
	}

	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers(HttpMethod.GET,
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
		).antMatchers(HttpMethod.POST, "/user", "/log", "/application");
	}

}
