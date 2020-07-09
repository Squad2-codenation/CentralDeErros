package br.com.codenation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CentralErrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(CentralErrosApplication.class, args);
	}

}
