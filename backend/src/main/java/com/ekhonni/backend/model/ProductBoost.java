/**
 * Author: Rifat Shariar Sakil
 * Time: 2:15 PM
 * Date: 2/12/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.model;


import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.BoostType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductBoost extends BaseEntity<Long> {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoostType boostType;

    @Column(nullable = false)
    private LocalDateTime boostedAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @PrePersist
    public void calculateExpiration() {
        this.boostedAt = LocalDateTime.now();
        this.expiresAt = this.boostedAt.plus(this.boostType.getDuration(), this.boostType.getUnit());

        int hour = this.expiresAt.getHour();

        if (hour <= 12) {
            this.expiresAt = this.expiresAt.plusDays(1).withHour(12).withMinute(0).withSecond(0).withNano(0);
        } else {
            this.expiresAt = this.expiresAt.plusDays(2).withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
    }
}