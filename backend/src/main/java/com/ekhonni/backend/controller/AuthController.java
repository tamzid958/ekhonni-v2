package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.AuthDTO;
import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Author: Md Jahid Hasan
 * Date: 12/12/24
 */
@RestController
@RequestMapping("/api/v2/auth")
public record AuthController(AuthService authService) {

    @PostMapping("/sign-in")
    public String signIn(@RequestBody AuthDTO authDTO) {
        return authService.signIn(authDTO);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public String create(@RequestBody UserDTO userDTO) {
        return authService.create(userDTO);
    }
}
