package com.bnl.bloodbank.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class BloodGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bloodGroupId;

    @NotNull(message = "Blood Group cannot be null")
    private String bloodGroupName;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0L, message = "Quantity should be at least 0")
    @Max(value = 10000L, message = "Quantity cannot be more than 10000")
    private long quantity;

//    @JoinColumn(name = "blood_bank_id")
    @ManyToOne
    @JsonBackReference(value = "bloodBankReference")
    BloodBank bloodBank;
}
