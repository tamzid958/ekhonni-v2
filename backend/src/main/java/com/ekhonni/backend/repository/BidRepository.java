package com.ekhonni.backend.repository;

import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Product;

/**
 * Author: Asif Iqbal
 * Date: 12/19/24
 */

public interface BidRepository extends BaseRepository<Bid, Long> {
    Product findProductById(Long id);
}
