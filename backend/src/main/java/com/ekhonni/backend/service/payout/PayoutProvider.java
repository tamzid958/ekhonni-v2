package com.ekhonni.backend.service.payout;

import com.ekhonni.backend.dto.withdraw.WithdrawRequest;
import com.ekhonni.backend.exception.payout.PayoutProcessingException;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */

public interface PayoutProvider {
    void processPayout(WithdrawRequest withdrawRequest) throws PayoutProcessingException;
}
