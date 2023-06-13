package com.bnl.bloodbank.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Request {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long requestId;

    @NotNull(message = "Blood Group should not be null")
    private String bloodGroup;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 1L, message = "Quantity should be at least 1")
    @Max(value = 10000L, message = "Quantity cannot be more than 10000")
    private long quantity;

    private LocalDate date;

    private String status;

    @ManyToOne
    @JsonBackReference(value = "donorReference")
//    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    private Users user;

}
