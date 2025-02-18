package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.ChatRoomResponseDTO;
import com.ekhonni.backend.model.ChatRoom;
import com.ekhonni.backend.service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v2/user/{userId}/chat/rooms")
@AllArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping
    public ChatRoomResponseDTO create(@PathVariable UUID userId, @RequestParam UUID user2Id) {
        return chatRoomService.create(userId, user2Id);
    }

    @GetMapping
    public List<ChatRoomResponseDTO> get(@PathVariable UUID userId) {
        return chatRoomService.get(userId);
    }
}
