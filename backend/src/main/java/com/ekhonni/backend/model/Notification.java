package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.NotificationStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Safayet Rafi
 * Date: 12/31/24
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id", nullable = false)
    private User recipient;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationStatus status;
}
