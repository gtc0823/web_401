package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.loginsystem")
@EntityScan("com.example.loginsystem.model")
@EnableJpaRepositories("com.example.loginsystem.repository")
public class LoginWebPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginWebPracticeApplication.class, args);
	}

}
