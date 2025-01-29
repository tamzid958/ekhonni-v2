package com.ekhonni.backend.dto;

import com.ekhonni.backend.enums.NotificationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record NotificationCreateRequestDTO(
        @NotNull(message = "Type cannot be null") NotificationType type,
        @NotBlank(message = "Message cannot be blank") String message,
        String redirectUrl) {
}
