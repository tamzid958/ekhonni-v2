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
}
