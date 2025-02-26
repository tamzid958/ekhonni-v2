package com.ekhonni.backend.service;


import com.ekhonni.backend.dto.ChatMessageRequestDTO;
import com.ekhonni.backend.dto.ChatMessageResponseDTO;
import com.ekhonni.backend.exception.user.UserNotFoundException;
import com.ekhonni.backend.model.ChatMessage;
import com.ekhonni.backend.model.ChatRoom;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.ChatMessageRepository;
import com.ekhonni.backend.repository.ChatRoomRepository;
import com.ekhonni.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatMessageService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    public ChatMessageResponseDTO create(ChatMessageRequestDTO chatMessageDTO){
        User user1 = userRepository.findById(chatMessageDTO.senderId())
                .orElseThrow( () -> new UserNotFoundException("User not found"));
        User user2 = userRepository.findById(chatMessageDTO.receiverId())
                .orElseThrow( () -> new UserNotFoundException("User not found"));


        ChatRoom chatRoom = chatRoomRepository.findByUsers(user1, user2)
                .orElseThrow(() -> new UserNotFoundException("Chat room not found"));

        ChatMessage chatMessage = new ChatMessage(
                chatRoom,
                chatMessageDTO.senderId(),
                chatMessageDTO.receiverId(),
                chatMessageDTO.content()
        );

        ChatMessage savedMessage = chatMessageRepository.save(chatMessage);

        return new ChatMessageResponseDTO(
                savedMessage.getChatRoom().getId(),
                savedMessage.getSenderId(),
                savedMessage.getReceiverId(),
                savedMessage.getContent()
        );
    }

    public List<ChatMessageResponseDTO> get(UUID chatRoomId){

        if (!chatRoomRepository.existsById(chatRoomId)) {
            throw new IllegalArgumentException("Chat room not found");
        }

        List<ChatMessage> chatMessage = chatMessageRepository.findByChatRoomId(chatRoomId);

        return chatMessage.stream()
                .map( messages -> new ChatMessageResponseDTO(
                        messages.getChatRoom().getId(),
                        messages.getSenderId(),
                        messages.getReceiverId(),
                        messages.getContent()
                ))
                .collect(Collectors.toList());
    }
}
