package com.ekhonni.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    @Value(("${spring.security.jwt.expiration}"))
    private long expiration;

    @Cacheable(value = "jwtBlackList", key = "#jwt", unless = "#result == null")
    public String getBlackListed(String jwt) {
        return null;
    }

    @CachePut(value = "jwtBlackList", key = "#jwt")
    public String blacklistToken(String jwt) {
        return jwt;
    }

    public String generate(Authentication authenticated) {
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
