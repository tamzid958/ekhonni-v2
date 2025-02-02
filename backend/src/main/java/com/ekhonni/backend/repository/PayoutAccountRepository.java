package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.PayoutAccount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Author: Asif Iqbal
 * Date: 2/2/25
 */
public interface PayoutAccountRepository extends BaseRepository<PayoutAccount, Long> {

    <P> Page<P> findByUserIdAndDeletedAtIsNull(UUID userId, Class<P> projection, Pageable pageable);

}
