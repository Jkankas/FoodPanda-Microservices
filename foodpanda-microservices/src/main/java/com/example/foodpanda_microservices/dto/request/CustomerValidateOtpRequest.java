package com.example.foodpanda_microservices.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerValidateOtpRequest {
    @NotBlank(message = "phoneNumber is mandatory")
    private String phoneNumber;
    @NotNull(message = "OTP cannot be Empty/Null")
    private Integer otp;
}
