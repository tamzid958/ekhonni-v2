package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.ChatMessageDTO;
import com.ekhonni.backend.service.ChatMessageService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;


@Controller
@AllArgsConstructor
public class ChatWebSocketController {


    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void sendMessage(ChatMessageDTO chatMessageDTO) {

        ChatMessageDTO savedMessage = chatMessageService.save(chatMessageDTO);
        messagingTemplate.convertAndSendToUser(
                chatMessageDTO.senderId().toString(),
                "/queue/messages",
                savedMessage
        );
        messagingTemplate.convertAndSendToUser(
                chatMessageDTO.receiverId().toString(),
                "/queue/messages",
                savedMessage
        );
    }
}
