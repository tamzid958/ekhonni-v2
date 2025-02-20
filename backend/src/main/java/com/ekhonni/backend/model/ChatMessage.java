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
    @JoinColumn(name = "chat_room_id" , nullable = false)
    private ChatRoom chatRoom;

    @Column(nullable = false)
    UUID senderId;

    @Column(nullable = false)
    UUID receiverId;

    @Column(nullable = false)
    private String content;

}
