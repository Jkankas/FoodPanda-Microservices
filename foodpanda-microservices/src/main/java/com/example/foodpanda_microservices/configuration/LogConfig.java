package com.example.foodpanda_microservices.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogConfig {

    @Bean
    public Logger logger(){
        return LoggerFactory.getLogger("Application");
    }

}
