package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.RefundStatus;
import com.ekhonni.backend.model.Refund;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Author: Asif Iqbal
 * Date: 1/20/25
 */

public interface RefundRepository extends BaseRepository<Refund, Long> {

    boolean existsByTransactionId(Long transactionId);

    Page<Refund> findByStatusIn(List<RefundStatus> statuses, Pageable pageable);
}
