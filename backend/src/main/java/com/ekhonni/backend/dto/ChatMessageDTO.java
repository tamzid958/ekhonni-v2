package com.ekhonni.backend.dto;

import java.time.LocalDateTime;
import java.util.UUID;


public record ChatMessageDTO(
        UUID senderId,
        UUID receiverId,
        String content
) {
}
