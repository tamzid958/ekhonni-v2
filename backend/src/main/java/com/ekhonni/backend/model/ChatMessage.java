package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 02/10/25
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage extends BaseEntity<UUID> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "chat_user1", referencedColumnName = "user1_id", insertable = false, updatable = false, nullable = false),
            @JoinColumn(name = "chat_user2", referencedColumnName = "user2_id", insertable = false, updatable = false, nullable = false)
    })
    private ChatRoom chatRoom;


    @Column(nullable = false)
    UUID senderId;

    @Column(nullable = false)
    UUID receiverId;

    @Column(nullable = false)
    private String content;

}
