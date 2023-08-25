package com.bnl.bloodbank;

import com.bnl.bloodbank.entity.Request;
import com.bnl.bloodbank.entity.Users;
import com.bnl.bloodbank.exception.NotPresentException;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.repository.UsersRepository;
import com.bnl.bloodbank.service.EmailService;
import com.bnl.bloodbank.serviceImpl.EmailServiceImpl;
import org.apache.catalina.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@TestPropertySource(properties = "email=test@example.com")
public class EmailTest {

    @Mock
    UsersRepository usersRepository;

    @Mock
    JavaMailSender javaMailSender;

    @InjectMocks
    EmailService emailService = new EmailServiceImpl();

    private Users user = Users.builder()
            .userId(1)
            .address("DTP")
            .city("Bangalore")
            .state("Karnataka")
            .gender("Male")
            .role("ROLE_ADMIN")
            .email("user@gmail.com")
            .password("password")
            .username("user")
            .phoneNumber(1234567890L)
            .dateOfBirth(LocalDate.of(2000, 10, 11))
            .otp("1234")
            .requests( new ArrayList<Request>(Arrays.asList(
                    new Request(1L, "A+", 10L, LocalDate.now(), "pending", this.user )
            )))
            .build();

    @Test
    void validVerifyOtp() throws UsernameNotFoundException {
        Mockito.when(usersRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Assertions.assertEquals(true, emailService.verifyOtp(user.getUsername(), "1234"));
    }

    @Test
    void invalidVerifyOtp() throws UsernameNotFoundException {
        Mockito.when(usersRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException ex = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> emailService.verifyOtp(user.getUsername(), "1234")
        );
        Assertions.assertEquals("Username not found", ex.getMessage());
    }

    @Test
    void validSendMail() throws UsernameNotFoundException{
        Mockito.when(usersRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Assertions.assertEquals("OTP sent to "+ user.getEmail(), emailService.sendSimpleMail(user.getUsername()));
    }

    @Test
    void invalidSendMail() throws UsernameNotFoundException{
        Mockito.when(usersRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        UsernameNotFoundException ex = Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> emailService.sendSimpleMail(user.getUsername())
        );
        Assertions.assertEquals("Username not found", ex.getMessage());
    }
}
