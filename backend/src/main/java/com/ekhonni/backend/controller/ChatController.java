package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.ChatRoomResponseDTO;
import com.ekhonni.backend.model.ChatRoom;
import com.ekhonni.backend.service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v2/user/{userId}/chat/rooms")
@AllArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;

    @PostMapping
    public ChatRoom create(@PathVariable UUID userId, @RequestParam UUID user2) {
        return chatRoomService.create(userId, user2);
    }

    @GetMapping
    public List<ChatRoomResponseDTO> get(@PathVariable UUID userId) {
        return chatRoomService.get(userId);
    }
}
