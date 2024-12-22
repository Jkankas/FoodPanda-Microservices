package com.example.foodpanda_microservices_warehouse.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class AppConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf-> csrf.disable()).authorizeHttpRequests(
                        auth-> auth.requestMatchers("/api/**").permitAll()
                                .anyRequest().authenticated())
                .cors(withDefaults());
        return httpSecurity.build();
    }






    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}





