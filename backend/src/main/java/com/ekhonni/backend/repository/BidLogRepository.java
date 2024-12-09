package com.ekhonni.backend.repository;

import com.ekhonni.backend.dto.BidLogDTO;
import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.projection.BidLogProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BidLogRepository extends JpaRepository<BidLog, Long> {

    @Query("SELECT bl.id as id, bl.amount AS amount, bl.status as status FROM BidLog bl WHERE bl.id = :id")
    BidLogProjection findProjectionById(Long id);

    @Query("SELECT bl.id AS id, bl.amount AS amount, bl.status AS status FROM BidLog bl")
    List<BidLogProjection> findAllProjection();

}