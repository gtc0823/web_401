package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.example.demo","com.example.loginsystem.controller","com.example.loginsystem.model",
"com.example.loginsystem.repository","com.example.loginsystem.service"})
public class LoginWebPracticeApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoginWebPracticeApplication.class, args);
	}

}
