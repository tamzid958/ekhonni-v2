package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.BlockPolicy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Author: Md Jahid Hasan
 * Date: 2/4/25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BlockInfo extends BaseEntity<Long> {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "blocked_by_id", nullable = false)
    private User blockedBy;

    @Column(nullable = false)
    private String reason;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BlockPolicy blockPolicy;

    @Column(nullable = false)
    private LocalDateTime blockedAt;

    @Column(nullable = false)
    private LocalDateTime unblockAt;

}
