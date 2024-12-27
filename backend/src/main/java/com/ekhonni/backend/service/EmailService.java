package com.ekhonni.backend.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
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

    public void sendVerificationEmail(String recipientEmail, String token) {

        String subject = "Email Verification";
        String verificationUrl = "http://localhost:8080/api/v2/auth/verify-email?token=" + token;
        String message = "Please click the following link to verify your email: " + verificationUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("MS_FWQO0k@trial-3yxj6ljrqe14do2r.mlsender.net");
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }


    public void sendPasswordResetEmail(String recipientEmail, String token) {

        String subject = "Password Reset";
        String resetUrl = "http://localhost:8080/api/v2/auth/reset-password?token=" + token;
        String message = "Please click the following link to reset your password: " + resetUrl;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom("MS_FWQO0k@trial-3yxj6ljrqe14do2r.mlsender.net");
        email.setTo(recipientEmail);
        email.setSubject(subject);
        email.setText(message);

        mailSender.send(email);
    }
}
