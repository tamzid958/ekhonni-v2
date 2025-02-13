package com.ekhonni.backend.repository;

import com.ekhonni.backend.dto.ChatMessageDTO;
import com.ekhonni.backend.model.ChatMessage;
import com.ekhonni.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessageDTO> findBySenderAndReceiver(User sender, User receiver);
}
