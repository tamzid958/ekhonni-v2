package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Bid;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends BaseRepository<Bid, Long> {
    List<Bid> findByProductId(Long productId);
}