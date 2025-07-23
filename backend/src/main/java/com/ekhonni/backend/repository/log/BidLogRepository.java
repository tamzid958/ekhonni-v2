package com.ekhonni.backend.repository.log;

import com.ekhonni.backend.model.log.BidLog;
import com.ekhonni.backend.repository.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

/**
 * Author: Asif Iqbal
 * Date: 2/6/25
 */
public interface BidLogRepository extends BaseRepository<BidLog, Long> {

    <P> Page<P> findByProductIdAndDeletedAtIsNull(Long productId, Pageable pageable);

    <P> Page<P> findByProductIdAndDeletedAtIsNull(Long productId, Class<P> projection, Pageable pageable);

    <P> Page<P> findByBidIdAndDeletedAtIsNull(Long bidId, Pageable pageable);

    <P> Page<P> findByCreatedAtBetweenAndDeletedAtIsNull(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    <P> Page<P> findByCreatedAtBetweenAndDeletedAtIsNull(LocalDateTime startDate, LocalDateTime endDate, Class<P> projection, Pageable pageable);

}
