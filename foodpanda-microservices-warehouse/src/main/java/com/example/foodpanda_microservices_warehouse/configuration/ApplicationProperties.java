package com.example.foodpanda_microservices_warehouse.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "menu.*")
public class ApplicationProperties {

        private String dish_list;
        private String token;

}
