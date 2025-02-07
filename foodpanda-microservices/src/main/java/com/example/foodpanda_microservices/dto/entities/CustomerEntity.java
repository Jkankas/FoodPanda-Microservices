package com.example.foodpanda_microservices.dto.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.springframework.aot.generate.GeneratedTypeReference;
import org.springframework.boot.autoconfigure.web.WebProperties;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_profile",uniqueConstraints = @UniqueConstraint(columnNames = "customer_id"))
public class CustomerEntity {


    @Column(name = "customer_id")
    private String customerId;
    private String fullName;
    @Id
    @Column(name = "phone_number",unique = true,nullable = false)
    private String phoneNumber;
    private String address;
    private String landmark;
    private Integer pin;
    private String city;
    private String state;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;




//    @OneToOne
//    @MapsId
//    @JoinColumn(name = "id", referencedColumnName = "id") // Make sure the names match DB columns
//    @JsonIgnore
//    private CustomerLoginMetadata customerLoginMetadata;

//    @Version // ✅ Optimistic Locking
//    private Integer version;

    /*
    phone_number is foreign key  as well as primary key  for the current entity -> customer profile
    referencedColumnName is primary key column for the parent entity -> customer login metadata.
    customerLoginMetadata -> Parent Table
    customerProfile -> Child Table.
     */

}
