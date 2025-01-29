package com.ekhonni.backend.util;

import com.ekhonni.backend.exception.RefreshTokenNotFoundException;
import com.ekhonni.backend.model.RefreshToken;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 * Date: 12/12/24
 * Author: Safayet Rafi
 * Date: 23/12/24
 */

@Service
public class TokenUtil {

    private final RefreshTokenRepository refreshTokenRepository;

    @Value("${spring.security.jwt.access.token.expiration}")
    private long accessTokenExpiration;

    @Value("${spring.security.refresh.token.expiration}")
    private long refreshTokenExpiration;

    @Value("${spring.security.jwt.secret}")
    private String secret;

    public TokenUtil(RefreshTokenRepository refreshTokenRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
    }

    public String generateJwtAccessToken(String subject) {
        return Jwts
                .builder()
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public RefreshToken generateRefreshToken() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] tokenBytes = new byte[32];
        secureRandom.nextBytes(tokenBytes);
        String value = Base64.getUrlEncoder().withoutPadding().encodeToString(tokenBytes);
        LocalDateTime expiration = LocalDateTime.now()
                .plus(refreshTokenExpiration, ChronoUnit.MILLIS);
        return new RefreshToken(value, expiration);
    }


    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    public String extractSubject(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody().getSubject();
    }


    public boolean isAccessTokenExpired(String jwt) {
        return extractAccessTokenExpiration(jwt).before(new Date());
    }


    private Date extractAccessTokenExpiration(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody().getExpiration();
    }


    public boolean isRefreshTokenValid(String token) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        RefreshToken refreshToken = refreshTokenRepository.getByValue(token);

        return isRefreshTokenBelongsToUser(user, refreshToken) && !isRefreshTokenExpired(refreshToken);
    }


    private boolean isRefreshTokenBelongsToUser(User user, RefreshToken providedRefreshToken) {
        RefreshToken userRefreshToken = user.getRefreshToken();

        if (userRefreshToken == null) throw new RefreshTokenNotFoundException();

        return userRefreshToken.getValue().equals(providedRefreshToken.getValue());
    }

    public boolean isRefreshTokenExpired(RefreshToken refreshToken) {
        return LocalDateTime.now().isAfter(refreshToken.getExpiration());
    }

    public String generateVerificationToken() {
        String token = UUID.randomUUID().toString();
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            mac.init(secretKeySpec);
            byte[] hmac = mac.doFinal(token.getBytes());
            return Base64.getUrlEncoder().encodeToString(hmac);
        } catch (Exception e) {
            throw new RuntimeException("Error generating HMAC token", e);
        }
    }
}
