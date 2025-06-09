package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailTaskDTO;
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
    private String hostEmail;


    public void send(EmailTaskDTO emailTaskDTO) {

        SimpleMailMessage email = new SimpleMailMessage();
        email.setFrom(hostEmail);
        email.setTo(emailTaskDTO.email());
        email.setSubject(emailTaskDTO.subject());
        email.setText(emailTaskDTO.message());

        mailSender.send(email);
    }

}
