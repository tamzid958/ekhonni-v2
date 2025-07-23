/**
 * Author: Rifat Shariar Sakil
 * Time: 4:20 PM
 * Date: 2/9/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.controller;

import com.ekhonni.backend.dto.ChatMessage;
import com.ekhonni.backend.model.Chat;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.service.ChatService;
import com.ekhonni.backend.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ChatController {

    private final ChatService chatService;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(ChatService chatService, SimpMessagingTemplate messagingTemplate) {
        this.chatService = chatService;
        this.messagingTemplate = messagingTemplate;
    }


    @MessageMapping("/chat.send")
    public void sendMessage(@Payload ChatMessage chatMessage) {
        System.out.println("hello");

        User sender = AuthUtil.getAuthenticatedUser();


        Chat chat = chatService.sendMessage(sender, UUID.fromString(chatMessage.getReceiverId()), chatMessage.getContent());

        messagingTemplate.convertAndSendToUser(
                chat.getReceiver().getUsername(),
                "/queue/messages",
                chat
        );
    }


    @GetMapping("/chat/history/{receiverId}")
    public List<Chat> getChatHistory(@PathVariable UUID receiverId) {
        User currentUser = AuthUtil.getAuthenticatedUser();
        return chatService.getChatHistory(currentUser, receiverId);
    }
    @GetMapping("/chat/gg")
    public String gg(){
        return "hello";
    }
}
