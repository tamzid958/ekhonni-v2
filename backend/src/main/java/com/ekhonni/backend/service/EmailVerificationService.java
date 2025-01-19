package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailTaskDTO;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.VerificationToken;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.repository.VerificationTokenRepository;
import com.ekhonni.backend.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Author: Safayet Rafi
 * Date: 22/12/24
 */

@Setter
@Getter
@AllArgsConstructor
@Service
public class EmailVerificationService {

    private final VerificationTokenService verificationTokenService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;
    private final EmailService emailService;
    private final EmailProducerService emailProducerService;


    @Value("${spring.constant.email-verification-url}")
    private String emailVerificationUrl;


    public void send(User user) {

        VerificationToken verificationToken;
        if (verificationTokenRepository.findByUser(user) != null) {
            verificationToken = verificationTokenService.replace(user);
        } else {
            verificationToken = verificationTokenService.create(user);
        }

        String recipientEmail = user.getEmail();
        String subject = "Email Verification";
        String url = emailVerificationUrl + verificationToken.getToken();
        String message = String.format(
                "Dear User,\n\n" +
                        "Thank you for registering with Ekhonni. To complete your registration, please verify your email address by clicking the link below:\n\n" +
                        "Verification Link: %s\n\n" +
                        "Thank you,\nThe Ekhonni Team",
                url
        );

        EmailTaskDTO emailTaskDTO = new EmailTaskDTO(
                recipientEmail,
                subject,
                message
        );

        emailProducerService.send(emailTaskDTO);
    }


    public String verify(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Verification Token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invalid Verification Token");
        }

        User user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        return "Email verified successfully!";
    }
}
