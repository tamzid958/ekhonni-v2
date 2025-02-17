package com.ekhonni.backend.repository;

import com.ekhonni.backend.compositekey.ChatRoomId;
import com.ekhonni.backend.dto.ChatRoomResponseDTO;
import com.ekhonni.backend.model.ChatRoom;
import com.ekhonni.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, ChatRoomId> {
    @Query("""
    SELECT c FROM ChatRoom c
    WHERE c.user1 = :user
       OR c.user2 = :user
""")
    List<ChatRoom> findByUser(@Param("user") User user);
}
