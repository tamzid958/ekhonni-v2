package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.ChatMessageRequestDTO;
import com.ekhonni.backend.dto.ChatMessageResponseDTO;
import com.ekhonni.backend.dto.ChatRoomResponseDTO;
import com.ekhonni.backend.service.ChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@AllArgsConstructor
public class ChatWebSocketController {


    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public ChatMessageResponseDTO sendMessage(ChatMessageRequestDTO chatMessageDTO) {

        ChatMessageResponseDTO savedMessage = chatMessageService.create(chatMessageDTO);

        String chatRoomTopic = "/queue/chat-room/" + savedMessage.chatRoomId();

        messagingTemplate.convertAndSend(chatRoomTopic, savedMessage);
        return savedMessage;
    }
}
