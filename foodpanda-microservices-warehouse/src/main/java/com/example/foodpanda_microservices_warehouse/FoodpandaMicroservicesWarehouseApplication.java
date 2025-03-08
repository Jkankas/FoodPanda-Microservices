package com.example.foodpanda_microservices_warehouse;

import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Map;

@SpringBootApplication
public class FoodpandaMicroservicesWarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodpandaMicroservicesWarehouseApplication.class, args);


//		String input = ""; // Example input
//		String[] ids = input.split("\\s+"); // Splits on any whitespace (new line, space, tab)
//		String formatted = "'" + String.join("','", ids) + "'";
//		System.out.println(formatted);
	}

}
