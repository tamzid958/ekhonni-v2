package com.ekhonni.backend.service;

import com.ekhonni.backend.compositekey.ChatRoomId;
import com.ekhonni.backend.dto.ChatMessageDTO;
import com.ekhonni.backend.exception.user.UserNotFoundException;
import com.ekhonni.backend.model.ChatMessage;
import com.ekhonni.backend.model.ChatRoom;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.ChatMessageRepository;
import com.ekhonni.backend.repository.ChatRoomRepository;
import com.ekhonni.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatMessageDTO save(ChatMessageDTO chatMessageDTO){
        User user1 = userRepository.findById(chatMessageDTO.senderId())
                .orElseThrow( () -> new UserNotFoundException("User not found"));
        User user2 = userRepository.findById(chatMessageDTO.receiverId())
                .orElseThrow( () -> new UserNotFoundException("User not found"));

        ChatRoomId chatRoomId = new ChatRoomId(
                user1.getId().compareTo(user2.getId()) < 0 ? user1.getId() : user2.getId(),
                user1.getId().compareTo(user2.getId()) < 0 ? user2.getId() : user1.getId()
        );

        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new UserNotFoundException("Chat room not found"));

        ChatMessage chatMessage = new ChatMessage(
                chatRoom,
                chatMessageDTO.senderId(),
                chatMessageDTO.receiverId(),
                chatMessageDTO.content()
        );

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        return new ChatMessageDTO(
                savedMessage.getSenderId(),
                savedMessage.getReceiverId(),
                savedMessage.getContent()
        );
    }
}
