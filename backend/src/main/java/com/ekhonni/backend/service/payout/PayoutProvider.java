package com.ekhonni.backend.service.payout;

import com.ekhonni.backend.exception.payout.PayoutProcessingException;
import com.ekhonni.backend.model.Withdraw;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */

public interface PayoutProvider {
    void processPayout(Withdraw withdraw) throws PayoutProcessingException;
}
