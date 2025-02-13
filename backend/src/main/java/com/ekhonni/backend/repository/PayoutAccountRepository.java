package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.PayoutCategory;
import com.ekhonni.backend.enums.PayoutMethod;
import com.ekhonni.backend.model.PayoutAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
public interface PayoutAccountRepository extends BaseRepository<PayoutAccount, Long> {

    <P> Page<P> findByAccountIdAndDeletedAtIsNull(Long accountId, Class<P> projection, Pageable pageable);

    boolean existsByAccountIdAndCategoryAndMethodAndPayoutAccountNumberAndDeletedAtIsNull(
            Long accountId, PayoutCategory category,
            PayoutMethod method, String payoutAccountNumber);

    boolean existsByAccountIdAndCategoryAndMethodAndPayoutAccountNumber(
            Long accountId, PayoutCategory category,
            PayoutMethod method, String payoutAccountNumber);

    Optional<PayoutAccount> findByAccountIdAndCategoryAndMethodAndPayoutAccountNumber(
            Long accountId, PayoutCategory category,
            PayoutMethod method, String payoutAccountNumber);

}
