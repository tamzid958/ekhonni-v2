package com.ekhonni.backend.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.UUID;

@Service
public class TokenUtil {

    @Value("${spring.security.jwt.secret}")
    private String secretKey;

    public String generate() {
        String token = UUID.randomUUID().toString();
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmac = mac.doFinal(token.getBytes());
            return Base64.getUrlEncoder().encodeToString(hmac);
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC token", e);
        }
    }
}
