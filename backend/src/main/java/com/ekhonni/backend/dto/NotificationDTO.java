package com.ekhonni.backend.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

/**
 * Author: Safayet Rafi
 * Date: 12/31/24
 */

public record NotificationDTO(

        @NotBlank(message = "title cannot be blank")
        String title,

        @NotBlank(message = "message cannot be blank")
        String message,

        @NotBlank(message = "createdAt cannot be blank")
        LocalDateTime createdAt
) {
}
