package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.withdraw.WithdrawRequest;
import com.ekhonni.backend.enums.WithdrawStatus;
import com.ekhonni.backend.exception.payoutaccount.PayoutAccountNotFoundException;
import com.ekhonni.backend.exception.withdraw.InsufficientBalanceException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.PayoutAccount;
import com.ekhonni.backend.model.Withdraw;
import com.ekhonni.backend.projection.withdraw.WithdrawProjection;
import com.ekhonni.backend.repository.WithdrawRepository;
import com.ekhonni.backend.util.AuthUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * Author: Asif Iqbal
 * Date: 2/3/25
 */
@Service
public class WithdrawService extends BaseService<Withdraw, Long> {

    private final WithdrawRepository withdrawRepository;
    private final AccountService accountService;
    private final PayoutAccountService payoutAccountService;

    @Value("${account.minimum-balance}")
    private double MINIMUM_BALANCE;

    @Value("${account.minimum-withdraw-amount}")
    private double MINIMUM_WITHDRAW_AMOUNT;

    public WithdrawService(WithdrawRepository withdrawRepository, AccountService accountService, PayoutAccountService payoutAccountService) {
        super(withdrawRepository);
        this.withdrawRepository = withdrawRepository;
        this.accountService = accountService;
        this.payoutAccountService = payoutAccountService;
    }

    @Transactional
    public Withdraw create(WithdrawRequest withdrawRequest) {
        Account account = accountService.getByUserId(AuthUtil.getAuthenticatedUser().getId());
        validateWithdrawalAmount(account, withdrawRequest.amount());

        PayoutAccount payoutAccount = payoutAccountService.get(withdrawRequest.payoutAccountId())
                .orElseThrow(PayoutAccountNotFoundException::new);

        Withdraw withdraw = new Withdraw();
        withdraw.setAccount(account);
        withdraw.setPayoutAccount(payoutAccount);
        withdraw.setAmount(withdrawRequest.amount());
        withdraw.setStatus(WithdrawStatus.PENDING);

        return withdrawRepository.save(withdraw);
    }

    private void validateWithdrawalAmount(Account account, double amount) {
        if (amount < MINIMUM_WITHDRAW_AMOUNT) {
            throw new IllegalArgumentException(String.format("Minimum withdrawal amount is: %s", MINIMUM_WITHDRAW_AMOUNT));
        }
        if (account.getBalance() - amount < MINIMUM_BALANCE) {
            throw new InsufficientBalanceException(
                    String.format("Withdrawal of %.2f would leave balance below minimum required balance of %.2f",
                            amount, MINIMUM_BALANCE)
            );
        }
    }

    public Page<WithdrawProjection> getAllForUser(Pageable pageable) {
        return withdrawRepository.findByAccountUserIdAndDeletedAtIsNull(AuthUtil.getAuthenticatedUser().getId(), WithdrawProjection.class, pageable);
    }

    public Page<WithdrawProjection> getAllByStatus(WithdrawStatus status, Pageable pageable) {
        return withdrawRepository.findByStatusAndDeletedAtIsNull(status, WithdrawProjection.class, pageable);
    }

    public Page<WithdrawProjection> getUserWithdrawsByStatus(WithdrawStatus status, Pageable pageable) {
        UUID userId = AuthUtil.getAuthenticatedUser().getId();
        return withdrawRepository.findByAccountUserIdAndStatusAndDeletedAtIsNull(userId, status, WithdrawProjection.class, pageable);
    }

    public Page<WithdrawProjection> getAllWithdraws(Pageable pageable) {
        return withdrawRepository.findBy(WithdrawProjection.class, pageable);
    }

    public Map<String, Object> getUserWithdrawStatistics(Long userId) {
        Map<String, Object> statistics = new HashMap<>();

        Double completedAmount = withdrawRepository.sumAmountByUserIdAndStatus(userId, WithdrawStatus.COMPLETED);
        Double pendingAmount = withdrawRepository.sumAmountByUserIdAndStatus(userId, WithdrawStatus.PENDING);

        statistics.put("totalCompletedAmount", completedAmount != null ? completedAmount : 0.0);
        statistics.put("totalPendingAmount", pendingAmount != null ? pendingAmount : 0.0);

        return statistics;
    }

    public Page<WithdrawProjection> getWithdrawsByDateRange(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return withdrawRepository.findByCreatedAtBetweenAndDeletedAtIsNull(startDate, endDate, WithdrawProjection.class, pageable);
    }

    public Page<WithdrawProjection> getAllForUserAdmin(UUID userId, Pageable pageable) {
        return withdrawRepository.findByAccountUserIdAndDeletedAtIsNull(userId, WithdrawProjection.class, pageable);
    }

    public Page<WithdrawProjection> getUserTransactionsByStatusAdmin(UUID userId, WithdrawStatus status, Pageable pageable) {
        return withdrawRepository.findByAccountUserIdAndStatusAndDeletedAtIsNull(userId, status, WithdrawProjection.class, pageable);
    }

}
