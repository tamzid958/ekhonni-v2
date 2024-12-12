package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.AuthDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Md Jahid Hasan
 * Date: 12/12/24
 */
@RestController
@RequestMapping("/api/v2/auth")
public record AuthController(AuthService authService) {

    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody AuthDTO authDTO) {

        String token = authService.signIn(authDTO);

        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body("Login successful");
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {

        String token = authService.create(userDTO);

        ResponseCookie cookie = ResponseCookie.from("JSESSIONID", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(7 * 24 * 60 * 60)
                .sameSite("Strict")
                .build();

        return ResponseEntity.ok()
                .header("Set-Cookie", cookie.toString())
                .body("Registration successful");
    }
}
