package com.example.foodpanda_microservices_Mangement.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AppConfig {




    @org.springframework.context.annotation.Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
