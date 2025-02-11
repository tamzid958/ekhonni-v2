package com.ekhonni.backend.service;

import com.ekhonni.backend.enums.TransactionStatus;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.CashIn;
import com.ekhonni.backend.projection.cashin.CashInProjection;
import com.ekhonni.backend.repository.CashInRepository;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/10/25
 */
@Slf4j
@Service
public class CashInService extends BaseService<CashIn, Long> {

    private final CashInRepository cashInRepository;
    private final AccountService accountService;

    public CashInService(CashInRepository cashInRepository, AccountService accountService) {
        super(cashInRepository);
        this.cashInRepository = cashInRepository;
        this.accountService = accountService;
    }

    @Transactional
    public CashIn create(Double amount) {
        Account account = accountService.getByUserId(AuthUtil.getAuthenticatedUser().getId());
        CashIn cashIn = new CashIn();
        cashIn.setAccount(account);
        cashIn.setAmount(amount);
        cashIn.setCurrency("BDT");
        cashIn.setStatus(TransactionStatus.PENDING);
        cashInRepository.save(cashIn);
        return cashIn;
    }

    @Modifying
    @Transactional
    public void updateSessionKey(CashIn cashIn, String sessionKey) {
        cashIn.setSessionKey(sessionKey);
    }

    @Modifying
    @Transactional
    public void updateStatus(CashIn cashIn, TransactionStatus status) {
        cashIn.setStatus(status);
    }

    public boolean existsByAccountId(Long accountId) {
        return cashInRepository.existsByAccountIdAndDeletedAtIsNull(accountId);
    }

    public Page<CashInProjection> getAllForAuthenticatedUser(Pageable pageable) {
        UUID userId = AuthUtil.getAuthenticatedUser().getId();
        return cashInRepository.findByAccountUserIdAndDeletedAtIsNull(
                userId, CashInProjection.class, pageable);
    }

    public Page<CashInProjection> getAllByStatus(TransactionStatus status, Pageable pageable) {
        return cashInRepository.findByStatusAndDeletedAtIsNull(status, CashInProjection.class, pageable);
    }

    public Page<CashInProjection> getUserCashInsByStatus(TransactionStatus status, Pageable pageable) {
        UUID userId = AuthUtil.getAuthenticatedUser().getId();
        return cashInRepository.findByAccountUserIdAndStatusAndDeletedAtIsNull(userId, status, CashInProjection.class, pageable);
    }

    public Page<CashInProjection> getAllCashIns(Pageable pageable) {
        return cashInRepository.findBy(CashInProjection.class, pageable);
    }

    public Page<CashInProjection> getCashInsByDateRange(
            LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        return cashInRepository.findByCreatedAtBetweenAndDeletedAtIsNull(startDate, endDate, CashInProjection.class, pageable);
    }

    public Page<CashInProjection> getAllForUserAdmin(UUID userId, Pageable pageable) {
        return cashInRepository.findByAccountUserIdAndDeletedAtIsNull(userId, CashInProjection.class, pageable);
    }

    public Page<CashInProjection> getUserCashInsByStatusAdmin(UUID userId, TransactionStatus status, Pageable pageable) {
        return cashInRepository.findByAccountUserIdAndStatusAndDeletedAtIsNull(userId, status, CashInProjection.class, pageable);
    }

    public Page<CashIn> getPendingCashInsOlderThan(TransactionStatus status, LocalDateTime timestamp, Pageable pageable) {
        return cashInRepository.findByStatusEqualsAndUpdatedAtLessThanEqualAndDeletedAtIsNull(status, timestamp, pageable);
    }
}