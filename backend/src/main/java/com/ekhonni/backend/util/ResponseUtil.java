package com.ekhonni.backend.util;

import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
public class ResponseUtil {
    public String generateCookie(String token){

        return ResponseCookie.from("session", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build().toString();
    }
}
