package com.ekhonni.backend.dto.account.payout;

import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;
import com.ekhonni.backend.validation.annotation.ValidPayoutAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
public record PayoutAccountUpdateDTO(

        @NotBlank(message = "Account number cannot be blank")
        String accountNumber,

        String bankName,
        String branchName,
        String accountHolderName,
        String routingNumber

) {

}
