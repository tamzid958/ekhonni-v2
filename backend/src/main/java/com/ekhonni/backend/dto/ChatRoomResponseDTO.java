package com.ekhonni.backend.dto;


import java.util.UUID;

public record ChatRoomResponseDTO(
        UUID receiverId,
        String receiverName
) {
}
