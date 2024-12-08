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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public double getBalance(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
        return account.getBalance();
    }

    public Account create(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Account account = new Account(user, 0.0, "Active");
        accountRepository.save(account);
        return account;
    }

    @Transactional
    public void softDelete(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
        account.setStatus("Deleted");
        account.setDeletedAt(LocalDateTime.now());
        accountRepository.save(account);
    }

    @Transactional
    public void delete(Long id) {
        accountRepository.deleteAccountById(id);
    }
}
