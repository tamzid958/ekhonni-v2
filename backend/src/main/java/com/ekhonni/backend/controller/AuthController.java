package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.AuthDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Md Jahid Hasan
 * Date: 12/12/24
 */
@RestController
@RequestMapping("/api/v2/auth")
@Validated
@AllArgsConstructor
public class AuthController {


    AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> signInUser(@RequestBody AuthDTO authDTO) {

        return ResponseEntity.ok(authService.signIn(authDTO));

    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(authService.create(userDTO));

    }


    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        // To be implemented
    }

}
