package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.RefundStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Asif Iqbal
 * Date: 1/20/25
 */

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Refund extends BaseEntity<Long> {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    private Transaction transaction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private User approvedBy;

    @Enumerated(EnumType.STRING)
    private RefundStatus status = RefundStatus.PENDING;

    @NotNull
    private String remarks;
    private Double amount = 0.0;
    private String refundReferenceId;
    private String bankTransactionId;

}
