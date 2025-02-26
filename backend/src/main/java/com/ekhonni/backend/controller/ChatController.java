package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.ChatMessageResponseDTO;
import com.ekhonni.backend.dto.ChatRoomResponseDTO;
import com.ekhonni.backend.model.ChatRoom;
import com.ekhonni.backend.service.ChatMessageService;
import com.ekhonni.backend.service.ChatRoomService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v2/user/{userId}/chat-rooms")
@AllArgsConstructor
public class ChatController {

    private final ChatRoomService chatRoomService;
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @PostMapping("/{otherUserId}/create")
    public ChatRoomResponseDTO createRoom(@PathVariable UUID userId, @PathVariable UUID otherUserId) {
        return chatRoomService.create(userId, otherUserId);
    }

    @GetMapping
    public List<ChatRoomResponseDTO> getRoom(@PathVariable UUID userId) {
        return chatRoomService.get(userId);
    }

    @GetMapping("/{chatRoomId}/messages")
    public List<ChatMessageResponseDTO> getMessage(@PathVariable UUID userId, @PathVariable UUID chatRoomId){
        return chatMessageService.get(chatRoomId);
    }

}
