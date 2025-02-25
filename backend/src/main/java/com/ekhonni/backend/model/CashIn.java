package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.PaymentMethod;
import com.ekhonni.backend.enums.TransactionStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CashIn extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionStatus status;

    @Enumerated(EnumType.STRING)
    @NotNull
    private PaymentMethod method;

    @NotNull
    private Double amount;
    @NotNull
    private String currency;

    private Double storeAmount;
    private Double bdtAmount;
    private String sessionKey;
    private String validationId;
    private String bankTransactionId;
    private LocalDateTime processedAt;

}
