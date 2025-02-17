package com.ekhonni.backend.service;

import com.ekhonni.backend.compositekey.ChatRoomId;
import com.ekhonni.backend.dto.ChatRoomResponseDTO;
import com.ekhonni.backend.exception.user.UserNotFoundException;
import com.ekhonni.backend.model.ChatRoom;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.ChatRoomRepository;
import com.ekhonni.backend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    public ChatRoom create(UUID user1Id, UUID user2Id){
        User user1 = userRepository.findById(user1Id)
                .orElseThrow( () -> new UserNotFoundException("User not found"));
        User user2 = userRepository.findById(user2Id)
                .orElseThrow( () -> new UserNotFoundException("User not found"));

        if (user1.equals(user2)) {
            throw new IllegalArgumentException("User cannot chat with themselves");
        }
        ChatRoom chatRoom = new ChatRoom(user1, user2, LocalDateTime.now(), null, null);
        return chatRoomRepository.save(chatRoom);
    }

    public List<ChatRoomResponseDTO> get(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow( () -> new UserNotFoundException("User not found"));

        List<ChatRoom> chatRooms = chatRoomRepository.findByUser(user);

        return chatRooms.stream()
                .map(chatRoom -> new ChatRoomResponseDTO(
                        new ChatRoomId(chatRoom.getUser1().getId(), chatRoom.getUser2().getId()),
                        chatRoom.getUser1().getName(),
                        chatRoom.getUser2().getName()
                ))
                .collect(Collectors.toList());
    }
}
