package com.bnl.bloodbank.controller;

import com.bnl.bloodbank.dto.EmailDetails;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send/{username}")
    public ResponseEntity<String> sendMail(@PathVariable String username) throws UsernameNotFoundException {
        return new ResponseEntity<>(emailService.sendSimpleMail(username), HttpStatus.OK);
    }

    @GetMapping("verifyOtp/{username}/{otp}")
    public ResponseEntity<Boolean> verifyEmail(@PathVariable String username, @PathVariable String otp) throws UsernameNotFoundException {
        return new ResponseEntity<Boolean>(emailService.verifyOtp(username, otp), HttpStatus.OK);
    }
}
