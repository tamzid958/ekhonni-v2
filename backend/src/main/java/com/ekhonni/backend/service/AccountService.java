/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.exception.AccountNotFoundException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountService extends BaseService<Account, Long> {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        super(accountRepository);
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public User getUser(Long id) {
        return accountRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public Account getSuperAdminAccount() {
        return accountRepository.findByRoleName("SUPER_ADMIN")
                .orElseThrow(() -> new AccountNotFoundException("Super admins account not found"));
    }

    @Transactional
    public Account create(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Account account = new Account(0.0, "Active");
        user.setAccount(account);
        accountRepository.save(account);
        return account;
    }

    public double getBalance(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));
        return account.getBalance();
    }

}
