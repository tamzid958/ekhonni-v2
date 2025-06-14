package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.dto.EmailTaskDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.enums.VerificationTokenType;
import com.ekhonni.backend.exception.InvalidVerificationTokenException;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.VerificationToken;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.repository.VerificationTokenRepository;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.util.AESUtil;
import com.ekhonni.backend.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@Service
public class EmailChangeService {

    private final VerificationTokenService verificationTokenService;
    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final AESUtil aesUtil;
    private final EmailService emailService;
    private final EmailProducerService emailProducerService;

    @Value("${email-change-url}")
    private String emailChangeUrl;


    public void request(User user, EmailDTO emailDTO) {

        VerificationToken verificationToken = verificationTokenService.generateForEmailChange(
                user,
                VerificationTokenType.CHANGE_EMAIL,
                emailDTO.email()
        );

        String recipientEmail = emailDTO.email();
        EmailTaskDTO emailTaskDTO = getEmailTaskDTO(verificationToken, recipientEmail);

        emailProducerService.send(emailTaskDTO);
    }

    private EmailTaskDTO getEmailTaskDTO(VerificationToken verificationToken, String recipientEmail) {
        String url = emailChangeUrl
                .replace("{id}", verificationToken.getUser().getId().toString())
                .replace("{token}", verificationToken.getToken());
        String subject = "Email Change Request";
        String message = String.format(
                "Dear User,\n\n" +
                        "Thank you for using Ekhonni. To change your email, please verify by clicking the link below:\n\n" +
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


    public String verifyAndUpdate(String token) {

        VerificationToken verificationToken = verificationTokenRepository.findByToken(token)
                .orElseThrow(() -> new InvalidVerificationTokenException("Invalid Verification Token"));

        if (verificationToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            System.out.println("1");
            throw new InvalidVerificationTokenException("Invalid Verification Token");
        }

        if (verificationToken.getType() != VerificationTokenType.CHANGE_EMAIL) {
            System.out.println("2");
            throw new InvalidVerificationTokenException("Invalid Verification Token");
        }

        String newEmail = aesUtil.extractEmail(token);

        User user = verificationToken.getUser();

        user.setEmail(newEmail);
        userRepository.save(user);

        verificationTokenRepository.delete(verificationToken);

        String responseMessage = "Email updated successfully!";
        return responseMessage;
    }
}
