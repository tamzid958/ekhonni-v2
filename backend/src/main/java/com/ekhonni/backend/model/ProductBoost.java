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
import lombok.*;

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
    }
}