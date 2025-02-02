package com.ekhonni.backend.dto.payoutaccount;

import jakarta.validation.constraints.NotBlank;

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
