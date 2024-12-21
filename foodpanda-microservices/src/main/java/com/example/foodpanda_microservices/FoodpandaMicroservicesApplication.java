package com.example.foodpanda_microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@SpringBootApplication
public class FoodpandaMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodpandaMicroservicesApplication.class, args);

//		User user = new User("username","sdfd", Collections.emptyList());
//		System.out.println(user);
//		user.getUsername();
//		System.out.println(user.getUsername());
	}

}
