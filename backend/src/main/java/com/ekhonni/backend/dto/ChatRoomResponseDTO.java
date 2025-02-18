package com.ekhonni.backend.dto;

import com.ekhonni.backend.compositekey.ChatRoomId;

import java.util.UUID;

public record ChatRoomResponseDTO(
        UUID receiverId,
        String receiverName
) {
}
