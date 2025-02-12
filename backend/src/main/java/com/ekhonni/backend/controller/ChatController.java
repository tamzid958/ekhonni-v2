///**
// * Author: Rifat Shariar Sakil
// * Time: 4:20 PM
// * Date: 2/9/25
// * Project Name: ekhonni-v2
// */
//
//package com.ekhonni.backend.controller;
//
//import com.ekhonni.backend.model.Chat;
//import com.ekhonni.backend.model.User;
//import com.ekhonni.backend.service.ChatService;
//import com.ekhonni.backend.util.AuthUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.handler.annotation.Header;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//@RequestMapping("/api/v2/chat")
//public class ChatController {
//
//    @Autowired
//    private ChatService chatService;
//
//    @Autowired
//    private SimpMessagingTemplate messagingTemplate;
//
//    @GetMapping("/history")
//    public ResponseEntity<List<Chat>> getChatHistory(@RequestParam UUID otherUserId) {
//        User authenticatedUser = AuthUtil.getAuthenticatedUser();
//        User otherUser = new User();
//        otherUser.setId(otherUserId);
//
//        List<Chat> chats = chatService.getChatHistory(authenticatedUser, otherUser);
//        return ResponseEntity.ok(chats);
//    }
//
//    @MessageMapping("/chat.sendMessage")
//    public void sendMessage(Chat chat, @Header("simpUser") User sender) {
//        chat.setSender(sender);
//        chat.setTimestamp(java.time.LocalDateTime.now());
//        messagingTemplate.convertAndSendToUser(
//                chat.getReceiver().getId().toString(), "/queue/messages", chat);
//    }
//}