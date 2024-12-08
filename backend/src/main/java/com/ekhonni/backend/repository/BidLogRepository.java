package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.BidLog;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BidLogRepository extends BaseRepository<BidLog, UUID> {

}