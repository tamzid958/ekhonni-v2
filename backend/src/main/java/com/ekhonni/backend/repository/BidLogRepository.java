package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.BidLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BidLogRepository extends JpaRepository<BidLog, UUID> {

}