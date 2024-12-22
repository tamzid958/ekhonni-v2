/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.exception.AccountNotFoundException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.UserProjection;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService extends BaseService<Account, Long> {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public UserProjection getUser(Long id) {
        return accountRepository.findUser(id);
    }

    @Transactional
    public Account create(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Account account = new Account(0.0, "Active");
        user.setAccount(account);
        accountRepository.save(account);
        return account;
    }

    public double getBalance(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
        return account.getBalance();
    }

    @Modifying
    @Transactional
    @Override
    public void softDelete(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
        account.setStatus("Deleted");
        accountRepository.softDelete(id);
    }

}
