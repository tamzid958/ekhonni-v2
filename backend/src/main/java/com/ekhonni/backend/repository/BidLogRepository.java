package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.BidLog;
import com.ekhonni.backend.projection.BidLogProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidLogRepository extends JpaRepository<BidLog, Long> {

    @Query("""
                SELECT bl.id AS id, 
                       bl.amount AS amount, 
                       bl.status AS status,
                       u AS bidder
                FROM BidLog bl 
                JOIN bl.bidder u
                WHERE bl.id = :id
            """)
    BidLogProjection findProjectionById(Long id);

    @Query("""
                SELECT bl.id AS id, 
                       bl.amount AS amount, 
                       bl.status AS status,
                       u AS bidder
                FROM BidLog bl
                JOIN bl.bidder u
            """)
    List<BidLogProjection> findAllProjection();
}
