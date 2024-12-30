package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.projection.BidLogProjection;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidLogRepository extends BaseRepository<Bid, Long> {

    @Query("SELECT bl.id as id, bl.amount AS amount, bl.status as status FROM Bid bl WHERE bl.id = :id")
    BidLogProjection findProjectionById(Long id);

    @Query("SELECT bl.id AS id, bl.amount AS amount, bl.status AS status FROM Bid bl")
    List<BidLogProjection> findAllProjection();

    Bid findBidById(Long Id);

}