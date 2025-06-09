package com.ekhonni.backend.service;

import com.ekhonni.backend.util.TokenUtil;
import lombok.AllArgsConstructor;
import org.jobrunr.scheduling.JobScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Md Jahid Hasan
 * Date: 2/25/25
 */
@Component
@AllArgsConstructor
public class TokenBlacklistService {

    private static final Logger logger = LoggerFactory.getLogger(TokenBlacklistService.class);
    private final Set<String> blacklistedTokens = Collections.newSetFromMap(new ConcurrentHashMap<>());
    private final JobScheduler jobScheduler;
    private final TokenUtil tokenUtil;


    public void blacklistToken(String token) {
        blacklistedTokens.add(token);
        scheduleTokenExpiryJob(token, tokenUtil.extractAccessTokenExpiration(token));

    }

    public boolean isBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }

    public Set<String> getBlacklisted() {
        return Collections.unmodifiableSet(blacklistedTokens);
    }

    public void removeFromBlacklist(String token) {
        logger.info("Scheduled task triggered: Removing token from blacklist: {}", token);
        blacklistedTokens.remove(token);
        System.out.println("BlackListed Token: " + getBlacklisted());
    }

    private void scheduleTokenExpiryJob(String token, Date expiryDate) {
        System.out.println(token + expiryDate + expiryDate.getTime());
        long delayMillis = expiryDate.getTime() - System.currentTimeMillis();
        if (delayMillis <= 0) {
            removeFromBlacklist(token);
            return;
        }

        jobScheduler.schedule(Instant.ofEpochMilli(expiryDate.getTime()), () -> removeFromBlacklist(token));
    }

}