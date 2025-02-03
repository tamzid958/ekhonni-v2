package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.payoutaccount.PayoutAccountCreateDTO;
import com.ekhonni.backend.exception.payoutaccount.PayoutAccountNotFoundException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.PayoutAccount;
import com.ekhonni.backend.repository.PayoutAccountProjection;
import com.ekhonni.backend.repository.PayoutAccountRepository;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
@Service
public class PayoutAccountService extends BaseService<PayoutAccount, Long> {

    private final PayoutAccountRepository payoutAccountRepository;
    private final AccountService accountService;

    public PayoutAccountService(PayoutAccountRepository payoutAccountRepository, AccountService accountService) {
        super(payoutAccountRepository);
        this.payoutAccountRepository = payoutAccountRepository;
        this.accountService = accountService;
    }

    @Transactional
    public void create(PayoutAccountCreateDTO dto) {
        Account account = accountService.getByUserId(AuthUtil.getAuthenticatedUser().getId());
        PayoutAccount payoutAccount = new PayoutAccount(
                account, dto.category(), dto.method(), dto.accountNumber(),
                dto.bankName(), dto.branchName(), dto.accountHolderName(), dto.routingNumber()
        );
        payoutAccountRepository.save(payoutAccount);
    }

    public Page<PayoutAccountProjection> getAllForUser(Pageable pageable) {
        Long accountId = accountService.getByUserId(AuthUtil.getAuthenticatedUser().getId()).getId();
        return payoutAccountRepository.findByAccountIdAndDeletedAtIsNull(accountId, PayoutAccountProjection.class, pageable);
    }

    public boolean isOwner(Long id, UUID userId) {
        PayoutAccount payoutAccount = get(id).orElseThrow(PayoutAccountNotFoundException::new);
        return payoutAccount.getAccount().getUser().getId().equals(userId);
    }

}
