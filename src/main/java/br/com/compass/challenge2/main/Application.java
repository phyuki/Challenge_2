package br.com.compass.challenge2.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("br.com.compass.challenge2.repository")
@EntityScan("br.com.compass.challenge2.entity")
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
