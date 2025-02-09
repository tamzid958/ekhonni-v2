package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Chat;
import com.ekhonni.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    List<Chat> findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(
            User sender, User receiver, User receiver2, User sender2);
}
