package com.ekhonni.backend.enums;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
@Getter
public enum PayoutCategory {
    MOBILE_BANKING(Arrays.asList(PayoutMethod.BKASH, PayoutMethod.ROCKET, PayoutMethod.NAGAD)),
    BANK(Arrays.asList(PayoutMethod.DBBL));

    private final List<PayoutMethod> payoutMethods;

    PayoutCategory(List<PayoutMethod> payoutMethods) {
        this.payoutMethods = payoutMethods;
    }

}

