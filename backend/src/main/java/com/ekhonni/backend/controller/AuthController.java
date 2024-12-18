package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.AuthDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.service.AuthService;
import com.ekhonni.backend.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
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
public record AuthController(AuthService authService, ResponseUtil responseUtil) {


    @PostMapping("/sign-in")
    public ResponseEntity<?> signIn(@RequestBody AuthDTO authDTO) {

        return ResponseEntity.ok(authService.signIn(authDTO));

    }


    @PostMapping("/sign-up")
    public ResponseEntity<?> create(@RequestBody UserDTO userDTO) {

        return ResponseEntity.ok(authService.create(userDTO));

    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> signOut() {
        return ResponseEntity.ok(authService.signOut());
    }
}
