package com.ekhonni.backend.service.payout.provider.bkash;

import com.ekhonni.backend.config.payout.BkashConfig;
import com.ekhonni.backend.enums.WithdrawStatus;
import com.ekhonni.backend.exception.payout.PayoutProcessingException;
import com.ekhonni.backend.model.Withdraw;
import com.ekhonni.backend.service.payout.PayoutProvider;
import com.ekhonni.backend.service.payout.provider.bkash.request.BkashPayoutRequest;
import com.ekhonni.backend.service.payout.provider.bkash.response.BkashPayoutResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
@Component
@AllArgsConstructor
public class BkashPayoutProvider implements PayoutProvider {

    private final BkashApiClient bkashApiClient;
    private final BkashConfig bkashConfig;
    private final BkashTokenManager bkashTokenManager;

    @Override
    @Transactional
    public void processPayout(Withdraw withdraw) throws PayoutProcessingException {

        updateWithdrawStatus(withdraw, WithdrawStatus.PROCESSING);

        BkashPayoutRequest payoutRequest = new BkashPayoutRequest(
                withdraw.getAmount().toString(),
                bkashConfig.getMerchantInvoiceNumber(),
                "BDT",
                withdraw.getPayoutAccount().getPayoutAccountNumber()
        );

        try {
//            BkashPayoutResponse response = bkashApiClient.sendMoney(payoutRequest, bkashTokenManager.getAccessToken());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss:SSS z")
                    .withZone(ZoneId.systemDefault());
            String formatted = formatter.format(LocalDateTime.now());
            BkashPayoutResponse response = new BkashPayoutResponse(
                    formatted, "trxId", "Completed", withdraw.getAmount(),
                    "BDT", withdraw.getPayoutAccount().getPayoutAccountNumber(),
                    "merchantInvoiceNumber", 0.0);
            updateWithdraw(withdraw, response);
        } catch (Exception e) {
            updateWithdrawStatus(withdraw, WithdrawStatus.FAILED);
            throw new PayoutProcessingException("exception");
//            throw e;
        }

    }

    @Transactional
    private void updateWithdraw(Withdraw withdraw, BkashPayoutResponse response) {
        if ("Completed".equals(response.getTransactionStatus())) {
            withdraw.setBdtAmount(response.getAmount());
            withdraw.setStatus(WithdrawStatus.COMPLETED);
            withdraw.setBankTransactionId(response.getTrxId());
            withdraw.setPayoutFee(response.getB2cFee());
            withdraw.setProcessedAt(response.getCompletedTimeAsDateTime());
        } else {
            withdraw.setStatus(WithdrawStatus.FAILED);
        }
    }

    @Transactional
    public void updateWithdrawStatus(Withdraw withdraw, WithdrawStatus status) {
        withdraw.setStatus(status);
    }

}
