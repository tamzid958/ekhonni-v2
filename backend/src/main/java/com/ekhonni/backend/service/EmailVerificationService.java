package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailTaskDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.enums.VerificationTokenType;
import com.ekhonni.backend.exception.InvalidVerificationTokenException;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.VerificationToken;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.repository.VerificationTokenRepository;
import com.ekhonni.backend.response.ApiResponse;
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


    public void request(User user) {

        VerificationToken verificationToken = verificationTokenService.generate(
                user,
                VerificationTokenType.EMAIL
        );

        String recipientEmail = user.getEmail();
        String subject = "Email Verification";
        EmailTaskDTO emailTaskDTO = getEmailTaskDTO(verificationToken, recipientEmail, subject);

        emailProducerService.send(emailTaskDTO);
    }

    private EmailTaskDTO getEmailTaskDTO(VerificationToken verificationToken, String recipientEmail, String subject) {
        String url = emailVerificationUrl + verificationToken.getToken();
        String message = String.format(
                "Dear User,\n\n" +
                        "Thank you for registering with Ekhonni. To complete your registration, please verify your email address by clicking the link below:\n\n" +
                        "Verification Link: %s\n\n" +
                        "Thank you,\nThe Ekhonni Team",
                url
        );

        return new EmailTaskDTO(
                recipientEmail,
                subject,
                message
        );
    }


    public ApiResponse<?> verify(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidVerificationTokenException("Invalid Verification Token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new InvalidVerificationTokenException("Invalid Verification Token");
        }

        if (verificationToken.getType() != VerificationTokenType.EMAIL) {
            throw new InvalidVerificationTokenException("Invalid Verification Token");
        }

        User user = verificationToken.getUser();
        user.setVerified(true);
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        String responseMessage = "Email verified successfully!";
        return new ApiResponse<>(HTTPStatus.OK, responseMessage);
    }
}
