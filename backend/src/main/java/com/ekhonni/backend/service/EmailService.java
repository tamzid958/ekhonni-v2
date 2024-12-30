package com.ekhonni.backend.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Author: Safayet Rafi
 * Date: 22/12/24
 */

@Setter
@Getter
@AllArgsConstructor
@Service
public class EmailService {

    @Autowired
    JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Value("${spring.constant.email-verification-url}")
    private String emailVerificationUrl;

    @Value("${spring.constant.password-reset-url}")
    private String passwordResetUrl;

    public void sendVerificationEmail(String recipientEmail, String token) {

        String subject = "Email Verification";
        String verificationUrl = emailVerificationUrl + token;
        String message = "Please click the following link to verify your email: " + verificationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(senderEmail);
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }


    public void sendPasswordResetEmail(String recipientEmail, String token) {

        String subject = "Password Reset";
        String resetUrl = passwordResetUrl + token;
        String message = "Please click the following link to reset your password: " + resetUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(senderEmail);
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}
