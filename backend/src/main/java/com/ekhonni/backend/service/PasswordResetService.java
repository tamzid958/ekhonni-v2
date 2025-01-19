package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailTaskDTO;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.VerificationToken;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


/**
 * Author: Safayet Rafi
 * Date: 25/12/24
 */

@Getter
@Setter
@AllArgsConstructor
@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final VerificationTokenService verificationTokenService;
    private final EmailService emailService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailProducerService emailProducerService;

    @Value("${spring.constant.password-reset-url}")
    private String passwordResetUrl;

    public String requestReset(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!user.isVerified()) {
            throw new RuntimeException("Email not verified. Please verify your email to sign in.");
        }

        VerificationToken verificationToken = verificationTokenService.create(user);

        String subject = "Password Reset Request";
        String url = passwordResetUrl + verificationToken.getToken();
        String message = String.format(
                "Dear User,\n\n" +
                        "We received a request to reset your password for your Ekhonni account.\n\n" +
                        "To proceed, visit the following link:\n%s\n\n" +
                        "If you did not request this, please ignore this email .\n\n" +
                        "Thank you,\nThe Ekhonni Team",
                url
        );

        EmailTaskDTO emailTaskDTO = new EmailTaskDTO(
                email,
                subject,
                message
        );
        emailProducerService.send(emailTaskDTO);

        return "A password reset link has been sent to your email. Please use the following link to reset your password";

    }


    public String reset(String token, String newPassword) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid Token"));

        User user = verificationToken.getUser();

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        return "Password Reset Successfully";
    }
}
