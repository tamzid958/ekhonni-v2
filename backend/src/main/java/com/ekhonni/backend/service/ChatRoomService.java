package com.ekhonni.backend.service;

import com.ekhonni.backend.model.ChatRoom;
import com.ekhonni.backend.repository.ChatRoomRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoom create(UUID user1, UUID user2){
        if(user1.equals(user2)){
            throw new IllegalArgumentException("User cannot chat with themeselves");
        }
        ChatRoom chatRoom = new ChatRoom(
                user1,
                user2,
                LocalDateTime.now(),
                null,
                null
        );
        return chatRoomRepository.save(chatRoom);
    }
}
