package com.ekhonni.backend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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


    public String generate(Authentication authenticated) {
        return Jwts
                .builder()
                .setSubject(authenticated.getName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String generate(String email) {
        System.out.println(new Date(System.currentTimeMillis()));
        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(generateKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public SecretKey generateKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractSubject(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody().getSubject();
    }

    public boolean isValid(String jwt, UserDetails user) {
        //checking if subject (email) found from jwt and user email found from
        // existing authentication token same or not, then checking is the token
        // is not expired yet
        return extractSubject(jwt).equals(user.getUsername()) && !isExpired(jwt);
    }


    private boolean isExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt) {
        return Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(jwt).getBody().getExpiration();
    }
}
