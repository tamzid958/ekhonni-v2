package com.ekhonni.backend.service.payout;

import com.ekhonni.backend.config.payout.BkashConfig;
import com.ekhonni.backend.dto.withdraw.WithdrawRequest;
import com.ekhonni.backend.exception.payout.PayoutProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
@Component
@AllArgsConstructor
public class BkashPayoutProvider implements PayoutProvider {

    private final BkashConfig bkashConfig;
    private final RestClient restClient;

    @Override
    public void processPayout(WithdrawRequest withdrawRequest) throws PayoutProcessingException {

    }
}
