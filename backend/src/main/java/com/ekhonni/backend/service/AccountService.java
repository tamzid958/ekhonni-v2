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
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public record AccountService(AccountRepository accountRepository, UserRepository userRepository) {

    public double getBalance(UUID accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);
        return account.getBalance();
    }

    public void create(UUID userId) {
        Optional<Account> existingAccount = accountRepository.findByUserId(userId);
        if (existingAccount.isPresent()) {
            throw new AccountNotFoundException();
        }

        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Account account = new Account();
        account.setUser(user);
        account.setStatus("ACTIVE");

        accountRepository.save(account);
    }
}
