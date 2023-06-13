package com.bnl.bloodbank.entity;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BloodBank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long bloodBankId;

    @NotNull(message = "Name cannot be null")
    private String name;

    @NotNull(message = "State cannot be null")
    private String state;

    @NotNull(message = "City cannot be null")
    private String city;

    @NotNull(message = "Address cannot be null")
    private String address;

    @NotNull(message = "Mobile number cannot be null")
    @Min(value = 1000000000L, message = "Mobile number should be of 10 digits")
    @Max(value = 9999999999L, message = "Mobile number should be of 10 digits")
    private long mobileNumber;

    private LocalDate lastUpdated;
    @OneToMany(mappedBy = "bloodBank" ,cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonManagedReference
    private List<BloodGroup> bloodgroups;
}
