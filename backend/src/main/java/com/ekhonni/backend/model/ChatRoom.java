package com.ekhonni.backend.model;


import com.ekhonni.backend.compositekey.ChatRoomId;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@IdClass(ChatRoomId.class)
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user1_id" , nullable = false)
    private UUID user1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user2_id", nullable = false)
    private UUID user2;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column
    private LocalDateTime deletedAt;

}
