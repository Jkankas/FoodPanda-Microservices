package com.example.foodpanda_microservices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
public class FoodpandaMicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodpandaMicroservicesApplication.class, args);

		String input = ""; // Example input
		String[] ids = input.split("\\s+"); // Splits on any whitespace (new line, space, tab)
		String formatted = "'" + String.join("','", ids) + "'";
		System.out.println(formatted);
	}

}
