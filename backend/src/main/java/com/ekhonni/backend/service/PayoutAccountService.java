package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.account.payout.PayoutAccountCreateDTO;
import com.ekhonni.backend.dto.account.payout.PayoutAccountUpdateDTO;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Account;
import com.ekhonni.backend.model.PayoutAccount;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.PayoutAccountRepository;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
@Service
public class PayoutAccountService extends BaseService<PayoutAccount, Long>{

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

}
