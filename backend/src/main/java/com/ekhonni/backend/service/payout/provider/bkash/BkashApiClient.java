package com.ekhonni.backend.service.payout.provider.bkash;

import com.ekhonni.backend.config.payout.BkashConfig;
import com.ekhonni.backend.exception.payout.PayoutProcessingException;
import com.ekhonni.backend.service.payout.provider.bkash.request.BkashPayoutRequest;
import com.ekhonni.backend.service.payout.provider.bkash.response.BkashPayoutResponse;
import com.ekhonni.backend.service.payout.provider.bkash.response.TokenResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Asif Iqbal
 * Date: 2/4/25
 */

@Component
@AllArgsConstructor
@Slf4j
public class BkashApiClient {

    private final BkashConfig bkashConfig;
    private final RestClient restClient;

    public TokenResponse grantToken() throws PayoutProcessingException {
        String url = bkashConfig.getGrantTokenUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("username", bkashConfig.getUsername());
        headers.add("password", bkashConfig.getPassword());

        Map<String, String> body = new HashMap<>();
        body.put("app_key", bkashConfig.getAppKey());
        body.put("app_secret", bkashConfig.getAppSecret());

        try {
            TokenResponse response = restClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(body)
                    .retrieve()
                    .body(TokenResponse.class);

            if (response != null) {
                return response;
            } else {
                throw new PayoutProcessingException("Error processing withdraw request");
            }

        } catch (RestClientException e) {
            log.error("Error getting grant token from bKash: {}", e.getMessage());
            throw new PayoutProcessingException("Error processing withdraw request");
        }
    }

    public TokenResponse refreshToken(String existingRefreshToken) throws PayoutProcessingException {
        String url = bkashConfig.getRefreshTokenUrl();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("username", bkashConfig.getUsername());
        headers.add("password", bkashConfig.getPassword());

        Map<String, String> body = new HashMap<>();
        body.put("app_key", bkashConfig.getAppKey());
        body.put("app_secret", bkashConfig.getAppSecret());
        body.put("refresh_token", existingRefreshToken);

        try {
            TokenResponse response = restClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(body)
                    .retrieve()
                    .body(TokenResponse.class);

            if (response != null) {
                return response;
            } else {
                throw new PayoutProcessingException("Error processing withdraw request");
            }

        } catch (RestClientException e) {
            log.error("Error refreshing token from bKash: {}", e.getMessage());
            throw new PayoutProcessingException("Error processing withdraw request");
        }
    }

    public BkashPayoutResponse sendMoney(BkashPayoutRequest payoutRequest, String accessToken) throws PayoutProcessingException {

        String url = bkashConfig.getDisbursementUrl();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("Authorization", accessToken);
        headers.add("X-APP-Key", bkashConfig.getXAppKey());

        try {
            BkashPayoutResponse response = restClient.post()
                    .uri(url)
                    .headers(httpHeaders -> httpHeaders.addAll(headers))
                    .body(payoutRequest)
                    .retrieve()
                    .body(BkashPayoutResponse.class);

            if (response != null) {
                return response;
            } else {
                throw new PayoutProcessingException("Error processing withdraw request");
            }

        } catch (Exception e) {
            log.error("Error sending money via bKash: {}", e.getMessage());
            throw new PayoutProcessingException("Error processing withdraw request");
        }
    }
}
