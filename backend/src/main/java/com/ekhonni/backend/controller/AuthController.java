package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.EmailDTO;
import com.ekhonni.backend.dto.user.AuthDTO;
import com.ekhonni.backend.dto.user.PasswordResetRequestDTO;
import com.ekhonni.backend.dto.user.ResetPasswordDTO;
import com.ekhonni.backend.dto.user.UserDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.model.AuthClaim;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.service.AuthService;
import com.ekhonni.backend.service.EmailVerificationService;
import com.ekhonni.backend.service.PasswordResetService;
import com.ekhonni.backend.service.ResendEmailService;
import com.ekhonni.backend.util.ResponseUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ApiResponse<AuthClaim>> signInUser(@RequestBody AuthDTO authDTO) {
        return ResponseUtil.createResponse(HTTPStatus.OK, authService.signIn(authDTO));
    }


    @PostMapping("/sign-up")
    public ResponseEntity<ApiResponse<String>> createUser(@RequestBody UserDTO userDTO) {
        return ResponseUtil.createResponse(HTTPStatus.CREATED, authService.create(userDTO));
    }


    @PostMapping("/resend-verification-email")
    public ResponseEntity<ApiResponse<String>> resendVerificationEmail(@Valid @RequestBody EmailDTO emailDTO) {
        return ResponseUtil.createResponse(HTTPStatus.OK, resendEmailService.reSend(emailDTO));
    }


    @GetMapping("/verify-email")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam("token") String token) {
        return ResponseUtil.createResponse(HTTPStatus.OK, emailVerificationService.verify(token));
    }


    @PostMapping("/password-reset-request")
    public ResponseEntity<ApiResponse<String>> requestPasswordReset(@RequestBody PasswordResetRequestDTO passwordResetDTO) {
        return ResponseUtil.createResponse(HTTPStatus.OK, passwordResetService.requestReset(passwordResetDTO.email()));
    }


    @PatchMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>> resetPassword(@RequestParam String token, @RequestBody ResetPasswordDTO resetPasswordDTO) {
        return ResponseUtil.createResponse(HTTPStatus.OK, passwordResetService.reset(token, resetPasswordDTO.newPassword()));
    }

}