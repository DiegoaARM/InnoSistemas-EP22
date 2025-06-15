package com.example.InnoSistemas;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class InnoSistemasApplication {

	@Autowired
	private Environment environment;

	public static void main(String[] args) {
		SpringApplication.run(InnoSistemasApplication.class, args);
	}

	@PostConstruct
	public void printMailUser() {
		System.out.println("Mail user: " + environment.getProperty("spring.mail.username"));
	}
}
