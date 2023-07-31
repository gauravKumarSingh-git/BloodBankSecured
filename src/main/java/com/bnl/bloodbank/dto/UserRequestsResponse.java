package com.bnl.bloodbank.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestsResponse {
    private String username;
    private String email;
    private String state;
    private String city;
    private String address;
    private long phoneNumber;
    private long requestId;
    private String bloodGroup;
    private long quantity;
    private LocalDate date;
    private String status;
}
