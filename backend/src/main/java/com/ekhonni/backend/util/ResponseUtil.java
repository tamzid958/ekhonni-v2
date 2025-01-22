package com.ekhonni.backend.util;

import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.response.ApiResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
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

    public static <T> ResponseEntity<ApiResponse<T>> createResponse(HTTPStatus status, T data) {
        return ResponseEntity.status(status.getCode()).body(new ApiResponse<>(status, data));
    }
}
