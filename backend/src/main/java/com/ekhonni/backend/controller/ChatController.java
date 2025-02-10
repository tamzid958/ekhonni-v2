package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.ChatMessageDTO;
import com.ekhonni.backend.model.ChatMessage;
import com.ekhonni.backend.service.ChatService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@AllArgsConstructor
public class ChatController {


    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/send")
    @SendTo("/topic/messages")
    public ChatMessageDTO sendMessage(ChatMessageDTO chatMessageDTO) {
        System.out.println(chatMessageDTO);
        return chatMessageDTO;
    }
}
