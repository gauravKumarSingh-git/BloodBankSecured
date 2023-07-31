package com.bnl.bloodbank.serviceImpl;

import com.bnl.bloodbank.dto.EmailDetails;
import com.bnl.bloodbank.entity.Users;
import com.bnl.bloodbank.exception.UsernameNotFoundException;
import com.bnl.bloodbank.repository.UsersRepository;
import com.bnl.bloodbank.service.EmailService;
import com.bnl.bloodbank.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UsersRepository usersRepository;

    @Value("${spring.mail.username}")
    private String sender;


    @Override
    public String sendSimpleMail(String username) throws UsernameNotFoundException {


            // Creating a simple mail message
            SimpleMailMessage mailMessage
                    = new SimpleMailMessage();

            Optional<Users> userFromRepo = usersRepository.findByUsername(username);
            Users user = userFromRepo.orElseThrow(() -> new UsernameNotFoundException("Username not found"));

            // Setting up necessary details
            mailMessage.setFrom(sender);
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("OTP for password change");
//            mailMessage.setText(details.getMsgBody());

            Random random = new Random();
            String otp = String.format("%04d", random.nextInt(10000));
            user.setOtp(otp);
            usersRepository.save(user);
            String msg = "Your OTP for password change is: " + otp + " It will expire in 5 minutes.";
            mailMessage.setText(msg);

            // Sending the mail
            javaMailSender.send(mailMessage);
            return "OTP sent to "+ user.getEmail();



    }

    @Override
    public Boolean verifyOtp(String username, String otp) throws UsernameNotFoundException {
        Optional<Users> userFromRepo = usersRepository.findByUsername(username);
        Users user = userFromRepo.orElseThrow(() -> new UsernameNotFoundException("Username not found"));
        if(user.getOtp().equals(otp)){
            return true;
        }else {
            return false;
        }
    }
}
