package com.ekhonni.backend.validation.validator;

import com.ekhonni.backend.dto.payoutaccount.PayoutAccountCreateDTO;
import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.validation.annotation.ValidPayoutAccount;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
public class PayoutAccountValidator implements ConstraintValidator<ValidPayoutAccount, PayoutAccountCreateDTO> {

    @Override
    public boolean isValid(PayoutAccountCreateDTO dto, ConstraintValidatorContext context) {
        if (dto.category() == PayoutCategory.BANK) {
            return !(isBlank(dto.bankName()) || isBlank(dto.branchName()) || isBlank(dto.accountHolderName()) || isBlank(dto.routingNumber()));
        }
        return true;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
