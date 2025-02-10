/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.TransactionStatus;
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
public class Transaction extends BaseEntity<Long> {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bid_id", nullable = false)
    private Bid bid;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    private Double storeAmount;
    private Double bdtAmount;
    private String sessionKey;
    private String validationId;
    private String bankTransactionId;
    private LocalDateTime processedAt;

    public Double getAmount() {
        return bid.getAmount();
    }

    public String getCurrency() {
        return bid.getCurrency();
    }

    public Product getProduct() {
        return bid.getProduct();
    }

    public User getBuyer() {
        return bid.getBidder();
    }

    public User getSeller() {
        return getProduct().getSeller();
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + getId() +
                ", status=" + status +
                ", sessionKey=" + sessionKey +
                ", updatedAt=" + getUpdatedAt() +
                "}";
    }
}
