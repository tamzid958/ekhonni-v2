/**
 * Author: Rifat Shariar Sakil
 * Time: 1:59 PM
 * Date: 2/10/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;


import com.ekhonni.backend.model.Chat;
import com.ekhonni.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query("SELECT c FROM Chat c " +
            "WHERE (c.sender = :user1 AND c.receiver = :user2) " +
            "   OR (c.sender = :user2 AND c.receiver = :user1) " +
            "ORDER BY c.timestamp ASC")
    List<Chat> findChatHistoryBetween(@Param("user1") User user1, @Param("user2") User user2);
}
