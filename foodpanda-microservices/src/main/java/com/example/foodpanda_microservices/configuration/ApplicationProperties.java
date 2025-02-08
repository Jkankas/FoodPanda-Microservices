package com.example.foodpanda_microservices.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "warehouse.*")
public class ApplicationProperties {

    private String stockByDish;
    private String priceByDish;
    private String updateStock;
}
