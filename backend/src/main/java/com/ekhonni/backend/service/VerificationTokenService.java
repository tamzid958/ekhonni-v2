package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.VerificationTokenType;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.model.VerificationToken;
import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.repository.VerificationTokenRepository;
import com.ekhonni.backend.util.AESUtil;
import com.ekhonni.backend.util.TokenUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


/**
 * Author: Safayet Rafi
 * Date: 22/12/24
 */


@Setter
@Getter
@AllArgsConstructor
@Service
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final AESUtil aesUtil;

    public VerificationToken create(String token, User user, VerificationTokenType type) {


        VerificationToken verificationToken = new VerificationToken(
                token,
                LocalDateTime.now().plusMinutes(5),
                user,
                type
        );

        return verificationTokenRepository.save(verificationToken);
    }

    public VerificationToken replace(String token, User user, VerificationTokenType type) {

        VerificationToken verificationToken = verificationTokenRepository.findByUserId(user.getId());

        verificationToken.setToken(token);
        verificationToken.setType(type);
        verificationToken.setExpiryDate(LocalDateTime.now().plusMinutes(5));

        return verificationTokenRepository.save(verificationToken);

    }

    public VerificationToken generate(User user, VerificationTokenType type){

        String rawToken = UUID.randomUUID().toString();
        String encryptedToken = aesUtil.encrypt(rawToken);

        VerificationToken verificationToken;
        if (verificationTokenRepository.findByUserId(user.getId()) != null) {
            verificationToken = replace(encryptedToken, user, type);
        } else {
            verificationToken = create(encryptedToken, user, type);
        }
        return verificationToken;
    }

    public VerificationToken generateForEmailChange(User user, VerificationTokenType type, String newEmail){

        String rawToken = UUID.randomUUID() + ":" + newEmail;
        String encryptedToken = aesUtil.encrypt(rawToken);

        VerificationToken verificationToken;
        if (verificationTokenRepository.findByUserId(user.getId()) != null) {
            verificationToken = replace(encryptedToken, user, type);
        } else {
            verificationToken = create(encryptedToken, user, type);
        }
        return verificationToken;
    }
}
