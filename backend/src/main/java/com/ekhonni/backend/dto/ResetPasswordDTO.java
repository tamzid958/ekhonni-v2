package com.ekhonni.backend.dto;

public record ResetPasswordDTO(String token, String newPassword) {
}
