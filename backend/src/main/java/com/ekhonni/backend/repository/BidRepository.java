package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Bid;
import org.springframework.stereotype.Repository;

@Repository
public interface BidRepository extends BaseRepository<Bid, Long> {
}