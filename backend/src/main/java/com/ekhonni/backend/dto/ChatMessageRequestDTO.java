package com.ekhonni.backend.dto;


import java.util.UUID;


public record ChatMessageRequestDTO(
        UUID chatRoomId,
        UUID senderId,
        UUID receiverId,
        String content
) {
}
