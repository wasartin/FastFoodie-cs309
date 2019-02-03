package com.will.exp3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class Exp3Application {

	public static void main(String[] args) {
		SpringApplication.run(Exp3Application.class, args);
		
		@RestController
		@RequestMapping("/api") 
		class ApiController{
			@GetMapping("/greeting")
			public String getGreeting() {
				return "Helo World from the API";
			}
		}
		
	}

}

