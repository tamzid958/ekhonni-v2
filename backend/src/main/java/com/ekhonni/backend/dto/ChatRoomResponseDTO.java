package com.ekhonni.backend.dto;

import com.ekhonni.backend.compositekey.ChatRoomId;

import java.util.UUID;

public record ChatRoomResponseDTO(
        ChatRoomId chatRoomId,
        String user1Name,
        String user2Name
) {
}
