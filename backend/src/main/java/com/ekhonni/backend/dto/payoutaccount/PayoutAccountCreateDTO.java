package com.ekhonni.backend.dto.payoutaccount;
import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;
import com.ekhonni.backend.validation.annotation.ValidPayoutAccount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */

@ValidPayoutAccount
public record PayoutAccountCreateDTO(

        @NotNull(message = "Payout category cannot be null")
        PayoutCategory category,

        @NotNull(message = "Payout method cannot be null")
        PayoutMethod method,

        @NotBlank(message = "Account number cannot be blank")
        String accountNumber,

        String bankName ,
        String branchName,
        String accountHolderName,
        String routingNumber

) {}
