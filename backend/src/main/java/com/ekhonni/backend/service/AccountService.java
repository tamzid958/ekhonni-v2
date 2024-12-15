/*
    Author: Asif Iqbal
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.AccountDTO;
import com.ekhonni.backend.exception.AccountNotFoundException;
import com.ekhonni.backend.exception.InvalidAccountStatusException;
import com.ekhonni.backend.exception.NegativeAmountException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.AccountProjection;
import com.ekhonni.backend.repository.AccountRepository;
import com.ekhonni.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountDTO get(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
        return new AccountDTO(account.getBalance(), account.getStatus());
    }

    public List<AccountProjection> getAll() {
        return accountRepository.getAll();
    }

    public List<AccountProjection> getAllDeleted() {
        return accountRepository.getAllDeleted();
    }

    public List<AccountProjection> getAllIncludingDeleted() {
        return accountRepository.getAllIncludingDeleted();
    }

    @Transactional
    public AccountDTO update(Long id, AccountDTO accountDTO) {
        if (accountDTO.balance() < 0) {
            throw new NegativeAmountException();
        }
        List<String> statusList = Arrays.asList("Active", "Pending", "Deleted", "Frozen");
        if (!statusList.contains(accountDTO.status())) {
            throw new InvalidAccountStatusException();
        }
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
        account.setBalance(accountDTO.balance());
        account.setStatus(accountDTO.status());
        return accountDTO;
    }

    public double getBalance(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(AccountNotFoundException::new);
        return account.getBalance();
    }

    public Account create(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        Account account = new Account(0.0, "Active");
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
