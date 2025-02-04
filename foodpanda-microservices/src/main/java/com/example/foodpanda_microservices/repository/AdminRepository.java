package com.example.foodpanda_microservices.repository;

import java.time.LocalDateTime;
import java.util.Map;

public interface AdminRepository {

    Map<String,Object> userProfile(String email);
    void createProfile(String department, String password, String role, String fullName, String email, int pin, String address1, String address2, LocalDateTime date, boolean is_active, String state, String city, String adminId);
}
