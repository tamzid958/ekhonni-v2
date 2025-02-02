package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.payoutaccount.PayoutAccountCreateDTO;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.exception.payoutaccount.PayoutAccountNotFoundException;
import com.ekhonni.backend.model.PayoutAccount;
import com.ekhonni.backend.model.User;
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
    private final UserService userService;

    public PayoutAccountService(PayoutAccountRepository payoutAccountRepository, UserService userService) {
        super(payoutAccountRepository);
        this.payoutAccountRepository = payoutAccountRepository;
        this.userService = userService;
    }

    @Transactional
    public void create(PayoutAccountCreateDTO dto) {
        User user = userService.get(AuthUtil.getAuthenticatedUser().getId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        PayoutAccount payoutAccount = new PayoutAccount(
                user, dto.category(), dto.method(), dto.accountNumber(),
                dto.bankName(), dto.branchName(), dto.accountHolderName(), dto.routingNumber()
        );
        payoutAccountRepository.save(payoutAccount);
    }

    public Page<PayoutAccountProjection> getAllForUser(Pageable pageable) {
        return payoutAccountRepository.findByUserIdAndDeletedAtIsNull(
                AuthUtil.getAuthenticatedUser().getId(), PayoutAccountProjection.class, pageable);
    }

    public boolean isOwner(Long id, UUID userId) {
        PayoutAccount payoutAccount = get(id).orElseThrow(PayoutAccountNotFoundException::new);
        return payoutAccount.getUser().getId().equals(userId);
    }

}
