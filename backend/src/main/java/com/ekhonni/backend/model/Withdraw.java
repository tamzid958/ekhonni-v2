package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.WithdrawStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 12/7/2024
 * Time: 9:56 PM
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Withdraw extends BaseEntity<Long> {
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "payout_account_id", nullable = false)
    private PayoutAccount payoutAccount;

    @Min(value = 50)
    @Column(nullable = false)
    private double amount;
    private String currency = "BDT";

    @NotNull
    @Enumerated(value = EnumType.STRING)
    private WithdrawStatus status;

    private Double bdtAmount;

    private Double payoutFee;
    private String bankTransactionId;
    private LocalDateTime processedAt;

}
