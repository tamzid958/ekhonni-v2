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
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {
    AccountRepository accountRepository;
    UserRepository userRepository;

    public double getBalance(Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(AccountNotFoundException::new);
        return account.getBalance();
    }

    public void create(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Account account = new Account(user, 0.0, "ACTIVE");
        accountRepository.save(account);
    }
}
