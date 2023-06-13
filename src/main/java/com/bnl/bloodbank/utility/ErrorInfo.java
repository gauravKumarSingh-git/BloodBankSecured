package com.bnl.bloodbank.utility;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorInfo {
    private String errorMessage;
    private int errorCode;
    private LocalDateTime timestamp;
}
