package com.ekhonni.backend.service;

import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.projection.BidLogProjection;
import com.ekhonni.backend.repository.BidLogRepository;
import org.springframework.stereotype.Service;

@Service
public record BidLogService(BidLogRepository bidLogRepository) {

    // create new bidLog
    public BidLog create(BidLog bidLog) {

        return bidLogRepository.save(bidLog);
    }
}
