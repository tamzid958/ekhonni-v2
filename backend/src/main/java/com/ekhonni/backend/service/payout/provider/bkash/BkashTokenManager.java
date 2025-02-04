package com.ekhonni.backend.service.payout.provider.bkash;

import com.ekhonni.backend.exception.payout.PayoutProcessingException;
import com.ekhonni.backend.service.payout.provider.bkash.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Author: Asif Iqbal
 * Date: 2/4/25
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class BkashTokenManager {

    private final ReentrantLock lock = new ReentrantLock();
    private final BkashApiClient bkashApiClient;

    private String accessToken;
    private String refreshToken;
    private Instant tokenExpirationTime;

    public String getAccessToken() throws PayoutProcessingException {
        if (!isTokenValid()) {
            refreshToken();
        }
        return accessToken;
    }

    private boolean isTokenValid() {
        return accessToken != null && tokenExpirationTime != null && Instant.now().isBefore(tokenExpirationTime.minusSeconds(600));
    }

    private void refreshToken() throws PayoutProcessingException {
        lock.lock();
        try {
            if (isTokenValid()) {
                return;
            }

            TokenResponse response;
            if (refreshToken != null) {
                response = bkashApiClient.refreshToken(refreshToken);
            } else {
                response = bkashApiClient.grantToken();
            }
            updateTokens(response);
        } catch (Exception e) {
            throw new PayoutProcessingException("Error processing withdraw request");
        } finally {
            lock.unlock();
        }
    }

    private void updateTokens(TokenResponse response) {
        this.accessToken = response.getIdToken();
        this.refreshToken = response.getRefreshToken();
        this.tokenExpirationTime = Instant.now().plusSeconds(response.getExpiresIn());
    }

}
