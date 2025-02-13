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
public class ChatController {


    private final ChatMessageService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    @SendTo("/topic/messages")
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO) {
        System.out.println(chatMessageDTO);
        return chatMessageDTO;
    }
}
