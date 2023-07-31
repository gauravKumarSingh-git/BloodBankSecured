package com.bnl.bloodbank.service;

import com.bnl.bloodbank.dto.EmailDetails;
import com.bnl.bloodbank.exception.UsernameNotFoundException;

public interface EmailService {
    String sendSimpleMail(String username) throws UsernameNotFoundException;

    Boolean verifyOtp(String username, String otp) throws  UsernameNotFoundException;
}
