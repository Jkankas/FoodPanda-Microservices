package com.example.foodpanda_microservices.dto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "customer_login_metadata")
public class CustomerLoginMetadata {

    @Id
    private long id;
    @Column(name = "phone_number")
    private String phoneNumber;
    private Integer otp;
    private LocalDateTime login_at;
    private boolean isActive;

//    @ManyToOne
//    @JoinColumn(name = "phone_number",referencedColumnName = "phone_number")
//    @JsonIgnore
//    private CustomerEntity customerEntity;






//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "phone_number", referencedColumnName = "phone_number") // Make sure the names match DB columns
//    @JsonIgnore
//    private CustomerEntity customerEntity;
}
