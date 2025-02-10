package com.ekhonni.backend.service.payout;

import com.ekhonni.backend.dto.withdraw.WithdrawRequest;
import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;
import com.ekhonni.backend.enums.WithdrawStatus;
import com.ekhonni.backend.exception.payout.PayoutProcessingException;
import com.ekhonni.backend.exception.payout.UnsupportedPayoutMethodException;
import com.ekhonni.backend.factory.payout.PayoutProviderFactory;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.Withdraw;
import com.ekhonni.backend.service.AccountService;
import com.ekhonni.backend.service.WithdrawService;
import com.ekhonni.backend.util.AuthUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
@Service
@AllArgsConstructor
@Slf4j
public class PayoutService {

    private final List<PayoutProviderFactory> payoutProviderFactories;
    private final WithdrawService withdrawService;
    private final AccountService accountService;

    @Modifying
    @Transactional
    public void processPayout(WithdrawRequest withdrawRequest) throws PayoutProcessingException {

        Withdraw withdraw = withdrawService.create(withdrawRequest);

        PayoutCategory payoutCategory = withdraw.getPayoutAccount().getCategory();
        PayoutMethod payoutMethod = withdraw.getPayoutAccount().getMethod();

        PayoutProviderFactory factory = getPayoutProviderFactory(payoutCategory, payoutMethod);

        PayoutProvider payoutProvider = factory.getPayoutProvider();
        payoutProvider.processPayout(withdraw);

        if (withdraw.getStatus().equals(WithdrawStatus.COMPLETED)) {
            updateAccountBalance(withdraw);
        }
    }

    private PayoutProviderFactory getPayoutProviderFactory(PayoutCategory category, PayoutMethod method) {
        return payoutProviderFactories.stream()
                .filter(f -> f.getPayoutCategory().equals(category)
                        && f.getPayoutMethod().equals(method))
                .findFirst()
                .orElseThrow(() -> new UnsupportedPayoutMethodException(
                        String.format("Unsupported payout category: %s or method: %s",
                                category, method)));
    }

    @Modifying
    @Transactional
    private void updateAccountBalance(Withdraw withdraw) {
        Account userAccount = accountService.getByUserId(AuthUtil.getAuthenticatedUser().getId());
        userAccount.setTotalWithdrawals(userAccount.getTotalWithdrawals() + withdraw.getAmount());

        Account superAdminAccount = accountService.getSuperAdminAccount();
        superAdminAccount.setTotalWithdrawals(superAdminAccount.getTotalWithdrawals() + withdraw.getAmount() + withdraw.getPayoutFee());
    }
}
