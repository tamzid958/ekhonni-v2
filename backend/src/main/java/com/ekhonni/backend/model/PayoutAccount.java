package com.ekhonni.backend.model;

import com.ekhonni.backend.baseentity.BaseEntity;
import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PayoutAccount extends BaseEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayoutCategory category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PayoutMethod method;

    @NotBlank
    @Column(nullable = false)
    private String payoutAccountNumber;

    private String bankName;
    private String branchName;
    private String accountHolderName;
    private String routingNumber;

}
