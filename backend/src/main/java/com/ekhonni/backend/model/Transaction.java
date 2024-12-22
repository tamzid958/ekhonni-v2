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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction extends BaseEntity<Long> {
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "bid_log_id", nullable = false)
    private BidLog bidLog;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status = TransactionStatus.PENDING;

    private String sessionkey;

    private double storeAmount;

    public double getAmount() {
        return bidLog.getAmount();
    }

    public User getBuyer() {
        return bidLog.getBidder();
    }

    public Account getBuyerAccount() {
        return getBuyer().getAccount();
    }

    public Product getProduct() {
        return bidLog.getBid().getProduct();
    }

    public User getSeller() {
        return getProduct().getUser();
    }

    public Account getSellerAccount() {
        return getSeller().getAccount();
    }
}
