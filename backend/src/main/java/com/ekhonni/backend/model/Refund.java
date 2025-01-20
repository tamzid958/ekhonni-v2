package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
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

    @NotNull
    private Double amount;
    @NotNull
    private String remarks;


    private String bankTransactionId;

}
