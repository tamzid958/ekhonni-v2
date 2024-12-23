package com.ekhonni.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Author: Md Jahid Hasan
 * Date: 12/12/24
 */

@Service
public class JWTUtil {

    @Value("${spring.security.jwt.secret}")
    private String secret;
    @Value(("${spring.security.jwt.access.token.expiration}"))
    private long accessTokenExpiration;
    @Value(("${spring.security.jwt.refresh.token.expiration}"))
    private long refreshTokenExpiration;

    public String generateAccessToken(Authentication authenticated) {
        return buildToken(authenticated, accessTokenExpiration);
    }

    public String generateRefreshToken(Authentication authenticated) {
        return buildToken(authenticated, refreshTokenExpiration);
    }

    private String buildToken(Authentication authenticated, long expiration) {
        return Jwts
                .builder()
                .setSubject(authenticated.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractSubject(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody().getSubject();
    }


    public boolean isExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody().getExpiration();
    }
}
