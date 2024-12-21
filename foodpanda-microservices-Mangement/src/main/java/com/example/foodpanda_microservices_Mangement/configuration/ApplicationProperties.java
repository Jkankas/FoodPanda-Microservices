package com.example.foodpanda_microservices_Mangement.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "menu.*")
@Data
public class ApplicationProperties {

    private  String menu_list;

}
