package com.ekhonni.backend.service;

import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.VerificationToken;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.repository.VerificationTokenRepository;
import com.ekhonni.backend.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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


    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;


    public String verifyEmail(String token) {

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
