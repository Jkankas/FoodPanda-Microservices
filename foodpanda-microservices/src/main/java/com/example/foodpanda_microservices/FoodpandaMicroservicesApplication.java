package com.example.foodpanda_microservices;

import com.example.foodpanda_microservices.dto.entities.UserEntity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@SpringBootApplication
public class FoodpandaMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodpandaMicroservicesApplication.class, args);

	}

}
