/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.TransactionStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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
    @OneToOne
    private Bid bid;

    private TransactionStatus status;
}
