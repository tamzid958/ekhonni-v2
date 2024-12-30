package com.ekhonni.backend.service;

import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.VerificationToken;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

    public String requestReset(String email) {

        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        VerificationToken verificationToken = verificationTokenService.create(user);

        emailService.sendPasswordResetEmail(email, verificationToken.getToken());

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
