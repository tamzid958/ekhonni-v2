package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private double amount;
    private String currency = "BDT";

    private Double storeAmount;
    private Double bdtAmount;
    private String sessionKey;
    private String validationId;
    private String bankTransactionId;
    private LocalDateTime processedAt;

}
