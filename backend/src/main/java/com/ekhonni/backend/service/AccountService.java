/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.AccountStatus;
import com.ekhonni.backend.exception.AccountNotFoundException;
import com.ekhonni.backend.exception.user.UserNotFoundException;
import com.ekhonni.backend.exception.withdraw.InsufficientBalanceException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.projection.account.AccountReportProjection;
import com.ekhonni.backend.projection.account.UserAccountProjection;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.service.UserService;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AccountService extends BaseService<Account, Long> {

    private final AccountRepository accountRepository;
    private final UserService userService;

    @Value("${account.minimum-balance}")
    private double MINIMUM_BALANCE;

    @Value("${account.minimum-withdraw-amount}")
    private double MINIMUM_WITHDRAW_AMOUNT;

    public AccountService(AccountRepository accountRepository, UserService userService) {
        super(accountRepository);
        this.accountRepository = accountRepository;
        this.userService = userService;
    }

    @Transactional
    public void create(UUID userId) {
        User user = userService.get(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Account account = new Account(user, 0.0, 0.0, AccountStatus.ACTIVE);
        accountRepository.save(account);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void withdraw(Account account, double amount) {
        validateMinimumBalance(account, amount);
        validateMinimumWithdrawalAmount(amount);
        account.setTotalWithdrawals(account.getTotalWithdrawals() + amount);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deposit(Account account, double amount) {
        account.setTotalEarnings(account.getTotalEarnings() + amount);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void transfer(Account senderAccount, Account receiverAccount, double amount) {
        validateMinimumBalance(senderAccount, amount);
        senderAccount.setTotalWithdrawals(senderAccount.getTotalWithdrawals() + amount);
        receiverAccount.setTotalEarnings(receiverAccount.getTotalEarnings() + amount);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void deduct(Account account, double amount) {
        validateMinimumBalance(account, amount);
        account.setTotalWithdrawals(account.getTotalWithdrawals() + amount);
    }

    @Transactional
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    public void remove(Account account, double amount) {
        account.setTotalWithdrawals(account.getTotalEarnings() - amount);
    }

    private void validateMinimumWithdrawalAmount(double amount) {
        if (amount < MINIMUM_WITHDRAW_AMOUNT) {
            throw new IllegalArgumentException(String.format("Minimum withdrawal amount is: %s", MINIMUM_WITHDRAW_AMOUNT));
        }
    }

    private void validateMinimumBalance(Account account, double amount) {
        if (account.getBalance() - amount < MINIMUM_BALANCE) {
            throw new InsufficientBalanceException(
                    String.format("Withdrawal of %.2f would leave balance below minimum required balance of %.2f",
                            amount, MINIMUM_BALANCE)
            );
        }
    }

    public UserProjection getUser(Long id) {
        return accountRepository.findUserById(id, UserProjection.class).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Account getByUserId(UUID userId) {
        return accountRepository.findByUserId(userId).orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    public Account getSuperAdminAccount() {
        return accountRepository.findSuperAdminAccount()
                .orElseThrow(() -> new AccountNotFoundException("Super admins account not found"));
    }

    public double getBalance(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account.getBalance();
    }

    public Double getAuthenticatedUserBalance() {
        return getByUserId(AuthUtil.getAuthenticatedUser().getId()).getBalance();
    }

    public UserAccountProjection getAuthenticatedUserAccount() {
        return accountRepository.findByUserId(AuthUtil.getAuthenticatedUser().getId(), UserAccountProjection.class)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public AccountReportProjection getAccountReport() {
        return accountRepository.getAccountReport();
    }

    @Transactional(readOnly = true, isolation = Isolation.REPEATABLE_READ)
    public AccountReportProjection getAccountReportBetween(LocalDateTime startTime, LocalDateTime endTime) {
        return accountRepository.getAccountReportBetween(startTime, endTime);
    }

}
