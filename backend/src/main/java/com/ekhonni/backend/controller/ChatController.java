package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.ChatMessageDTO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/user/{userId}/chats")
public class ChatController {

    @MessageMapping(" /send")
    @SendTo("/topic/messages")
    public ChatMessageDTO sendMessage(ChatMessageDTO message){
        return message;
    }
}
