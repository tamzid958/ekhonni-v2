package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.*;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.AuthService;
import com.ekhonni.backend.service.EmailVerificationService;
import com.ekhonni.backend.service.PasswordResetService;
import com.ekhonni.backend.service.ResendEmailService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    EmailVerificationService emailVerificationService;
    PasswordResetService passwordResetService;
    ResendEmailService resendEmailService;

    @PostMapping("/sign-in")
    @PreAuthorize("@userService.isActive(#authDTO.email())")
    public ResponseEntity<?> signInUser(@RequestBody AuthDTO authDTO) {


        return ResponseEntity.ok(authService.signIn(authDTO));

    }


    @PostMapping("/sign-up")
    public ApiResponse<?> createUser(@RequestBody UserDTO userDTO) {

        return authService.create(userDTO);

    }

    @GetMapping("/verify-email")
    public ApiResponse<?> verifyEmail(@RequestParam("token") String token) {
        return emailVerificationService.verify(token);
    }

    @PostMapping("/resend-verification-email")
    public ApiResponse<?> resendVerificationEmail(@Valid @RequestBody EmailDTO emailDTO) {
        return resendEmailService.reSend(emailDTO);
    }

    @PostMapping("/password-reset-request")
    public ApiResponse<?> requestPasswordReset(@RequestBody PasswordResetRequestDTO passwordResetDTO) {
        return passwordResetService.requestReset(passwordResetDTO.email());
    }

    @PatchMapping("/reset-password")
    public ApiResponse<?> resetPassword(@RequestParam String token, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        return passwordResetService.reset(token, resetPasswordDTO.newPassword());
    }

}
