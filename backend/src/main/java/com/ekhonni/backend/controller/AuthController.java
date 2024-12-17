package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.LoginDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/auth")
public record AuthController(AuthService authService) {

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO) {

        return ResponseEntity.ok(authService.authenticateUser(loginDTO));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/register")
    public UserDTO createUser(@RequestBody UserDTO userDTO) {
        return authService.create(userDTO);
    }
}
