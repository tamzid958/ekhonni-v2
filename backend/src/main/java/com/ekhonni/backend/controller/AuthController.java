package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.*;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.AuthService;
import com.ekhonni.backend.service.EmailVerificationService;
import com.ekhonni.backend.service.PasswordResetService;
import com.ekhonni.backend.service.ResendEmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication")
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


    @Operation(
            description = "Resends a email verification link to the user's email address",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Verification email resent successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PostMapping("/resend-verification-email")
    public ApiResponse<?> resendVerificationEmail(@Valid @RequestBody EmailDTO emailDTO) {
        return resendEmailService.reSend(emailDTO);
    }


    @Operation(
            description = "Verifies the email using the token sent to the user's email address.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Email verified successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid verification token")
            }
    )
    @GetMapping("/verify-email")
    public ApiResponse<?> verifyEmail(@RequestParam("token") String token) {
        return emailVerificationService.verify(token);
    }


    @Operation(
            description = "Sends a password reset link to the user's email address.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password reset email sent successfully"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "User not found")
            }
    )
    @PostMapping("/password-reset-request")
    public ApiResponse<?> requestPasswordReset(@RequestBody PasswordResetRequestDTO passwordResetDTO) {
        return passwordResetService.requestReset(passwordResetDTO.email());
    }


    @Operation(
            description = "Resets the user's password using the provided token and new password.",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Password reset successful"),
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid verification token")
            }
    )
    @PatchMapping("/reset-password")
    public ApiResponse<?> resetPassword(@RequestParam String token, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        return passwordResetService.reset(token, resetPasswordDTO.newPassword());
    }

}
