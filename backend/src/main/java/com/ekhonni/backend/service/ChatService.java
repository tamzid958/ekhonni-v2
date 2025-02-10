package com.ekhonni.backend.service; /**
 * Author: Rifat Shariar Sakil
 * Time: 4:26 PM
 * Date: 2/9/25
 * Project Name: ekhonni-v2
 */



import com.ekhonni.backend.model.Chat;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.ChatRepository;
import com.ekhonni.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public Chat sendMessage(User sender, UUID receiverId, String content) {
        System.out.println(sender + " " + receiverId + "  " + content);
        Optional<User> receiverOpt = userRepository.findById(receiverId);
        if (!receiverOpt.isPresent()) {
            throw new RuntimeException("Receiver not found");
        }
        User receiver = receiverOpt.get();

        Chat chat = new Chat();
        chat.setSender(sender);
        chat.setReceiver(receiver);
        chat.setContent(content);
        chat.setTimestamp(LocalDateTime.now());

        return chatRepository.save(chat);
    }


    public List<Chat> getChatHistory(User user1, UUID user2Id) {
        Optional<User> user2Opt = userRepository.findById(user2Id);
        if (!user2Opt.isPresent()) {
            throw new RuntimeException("User not found");
        }
        User user2 = user2Opt.get();

        return chatRepository.findChatHistoryBetween(user1, user2);
    }
}
