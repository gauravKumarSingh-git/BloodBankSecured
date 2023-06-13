package com.bnl.bloodbank.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @NotNull(message = "Username cannot be null")
    @Pattern(regexp = "[a-zA-Z0-9]+( [A-Za-z0-9]+)*", message = "Username should contain only alphabets and numerical values")
    @Size(min=5, message = "Username should be at least 5 characters long")
    @Size(max = 15, message = "Username should be less than 16 characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password should be at least 8 characters long")
    @Size(max = 60, message = "Password should be less than 60 characters")
    private String password;

    @Email(message = "Invalid email")
    @NotNull(message = "Email should not be null")
    private String email;

    @NotNull(message = "State should not be null")
    private String state;

    @NotNull(message = "City should not be null")
    private String city;

    @NotNull(message = "Address should not be null")
    private String address;

    private LocalDate dateOfBirth;

    private String gender;

    @NotNull(message = "Mobile number cannot be null")
    @Min(value = 1000000000L, message = "Mobile number should be of 10 digits")
    @Max(value = 9999999999L, message = "Mobile number should be of 10 digits")
    private long phoneNumber;

    @NotNull(message = "Role cannot be null")
    private String role;

    @OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "donorReference")
//    @JsonIgnoreProperties(value = {"hibernateLazyInitializer"})
    private List<Request> requests;
}
