/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.service;

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
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isEmpty()) {
            throw new RuntimeException("Account does not exist.");
        }
        return account.get().getBalance();
    }

    public void create(UUID userId) {
        Optional<Account> existingAccount = accountRepository.findByUserId(userId);
        if (existingAccount.isPresent()) {
            throw new RuntimeException("User account already exists.");
        }

        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("User not found.");
        }
        Account account = new Account();
        account.setUser(user.get());
        account.setStatus("ACTIVE");

        accountRepository.save(account);
    }

    public void delete(UUID id) {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isEmpty()) {
            throw new RuntimeException("Account does not exist.");
        }
        account.get().setStatus("DELETED");
        account.get().setDeleted(false);
    }
}
