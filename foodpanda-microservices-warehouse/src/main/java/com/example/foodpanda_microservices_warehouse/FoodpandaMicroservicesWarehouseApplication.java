package com.example.foodpanda_microservices_warehouse;

import com.example.foodpanda_microservices_warehouse.dto.response.ApiResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FoodpandaMicroservicesWarehouseApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodpandaMicroservicesWarehouseApplication.class, args);

		ApiResponse response = new ApiResponse();
		response.setResult("testing....");
		System.out.println(response);
	}

}
