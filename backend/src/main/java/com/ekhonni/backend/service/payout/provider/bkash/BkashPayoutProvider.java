package com.ekhonni.backend.service.payout.provider.bkash;

import com.ekhonni.backend.config.payout.BkashConfig;
import com.ekhonni.backend.dto.withdraw.WithdrawRequest;
import com.ekhonni.backend.exception.payout.PayoutProcessingException;
import com.ekhonni.backend.exception.payoutaccount.PayoutAccountNotFoundException;
import com.ekhonni.backend.model.PayoutAccount;
import com.ekhonni.backend.service.PayoutAccountService;
import com.ekhonni.backend.service.payout.PayoutProvider;
import com.ekhonni.backend.service.payout.PayoutService;
import com.ekhonni.backend.service.payout.provider.bkash.request.BkashPayoutRequest;
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

    private final BkashApiClient bkashApiClient;
    private final BkashConfig bkashConfig;
    private final PayoutAccountService payoutAccountService;
    private final BkashTokenManager bkashTokenManager;

    @Override
    public void processPayout(WithdrawRequest withdrawRequest) throws PayoutProcessingException {
        PayoutAccount payoutAccount = payoutAccountService.get(withdrawRequest.payoutAccountId())
                .orElseThrow(PayoutAccountNotFoundException::new);

        BkashPayoutRequest payoutRequest = new BkashPayoutRequest(
                withdrawRequest.amount().toString(),
                bkashConfig.getMerchantInvoiceNumber(),
                "BDT",
                payoutAccount.getPayoutAccountNumber()
        );

        bkashApiClient.sendMoney(payoutRequest, bkashTokenManager.getAccessToken());
    }
}
