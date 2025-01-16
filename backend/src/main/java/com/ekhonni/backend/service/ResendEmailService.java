package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ResendEmailService {
    private final EmailVerificationService emailVerificationService;
    private final UserRepository userRepository;

    public String reSend(EmailDTO emailDTO) {
        User user = userRepository.findByEmail(emailDTO.email());
        if (user == null) throw new UserNotFoundException("User not found");
        return emailVerificationService.send(user);
    }
}
