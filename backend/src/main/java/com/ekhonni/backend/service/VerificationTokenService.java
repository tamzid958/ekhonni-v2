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
public class VerificationTokenService {

    private final VerificationTokenRepository verificationTokenRepository;
    private final UserRepository userRepository;
    private final TokenUtil tokenUtil;

    public VerificationToken create(User user) {

        String token = tokenUtil.generate();

        VerificationToken verificationToken = new VerificationToken(
                token,
                LocalDateTime.now().plusMinutes(2),
                user
        );

        return verificationTokenRepository.save(verificationToken);
    }

}
