package com.ekhonni.backend.validation.validator;

import com.ekhonni.backend.dto.payoutaccount.PayoutAccountCreateDTO;
import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;
import com.ekhonni.backend.validation.annotation.ValidPayoutAccount;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.Map;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
public class PayoutAccountValidator implements ConstraintValidator<ValidPayoutAccount, PayoutAccountCreateDTO> {

    private static final Map<PayoutCategory, List<PayoutMethod>> VALID_CATEGORY_METHODS = Map.of(
            PayoutCategory.BANK, List.of(PayoutMethod.DBBL),
            PayoutCategory.MOBILE_BANKING, List.of(
                    PayoutMethod.BKASH,
                    PayoutMethod.ROCKET,
                    PayoutMethod.NAGAD
            )
    );

    @Override
    public boolean isValid(PayoutAccountCreateDTO dto, ConstraintValidatorContext context) {
        if (!isValidCategoryMethodPair(dto.category(), dto.method())) {
            addConstraintViolation(context,
                    String.format("Invalid payout method '%s' for category '%s'. Valid methods are: %s",
                            dto.method(),
                            dto.category(),
                            VALID_CATEGORY_METHODS.get(dto.category())
                    )
            );
            return false;
        }

        if (dto.category() == PayoutCategory.BANK) {
            if (isBlank(dto.bankName()) || isBlank(dto.branchName()) ||
                    isBlank(dto.accountHolderName()) || isBlank(dto.routingNumber())) {
                addConstraintViolation(context,
                        "Bank category requires bankName, branchName, accountHolderName, and routingNumber"
                );
                return false;
            }
        }

        return true;
    }

    private boolean isValidCategoryMethodPair(PayoutCategory category, PayoutMethod method) {
        List<PayoutMethod> validMethods = VALID_CATEGORY_METHODS.get(category);
        return validMethods != null && validMethods.contains(method);
    }

    private void addConstraintViolation(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}