package com.ekhonni.backend.service;

import com.ekhonni.backend.repository.UserRepository;
import com.ekhonni.backend.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 23/12/24
 */

@Setter
@Getter
@AllArgsConstructor
@Service
public class CleanupService {

    private final UserRepository userRepository;
    private final VerificationTokenRepository verificationTokenRepository;

    @Scheduled(cron = "0 0 0 * * *")
    public void removeExpiredVerificationTokensAndUnverifiedUsers() {

        LocalDateTime now = LocalDateTime.now();

        List<UUID> userIds = verificationTokenRepository.findUnverifiedUserIdsWithExpiredTokens(now);
        verificationTokenRepository.deleteByExpiryDateBefore(now);
        if (!userIds.isEmpty()) {
            userRepository.deleteByIdIn(userIds);
        }
    }
}
