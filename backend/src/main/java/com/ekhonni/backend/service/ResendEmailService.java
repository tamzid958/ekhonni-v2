package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Author: Safayet Rafi
 * Date: 16/01/25
 */

@AllArgsConstructor
@Service
public class ResendEmailService {
    private final UserRepository userRepository;
    private final EmailVerificationService emailVerificationService;

    public String reSend(EmailDTO emailDTO) {
        User user = userRepository.findByEmail(emailDTO.email());
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        emailVerificationService.send(user);
        return "A verification link has been sent to your email. Please verify your email to sign in.";
    }
}
