package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Bid;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BidRepository extends BaseRepository<Bid, Long> {
    List<Bid> findByProductId(Long productId);

    @Query("SELECT b.bidder.id FROM Bid b WHERE b.id = :id")
    Optional<UUID> findBidderIdById(Long id);
}