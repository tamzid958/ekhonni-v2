package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.BidCreateDTO;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.repository.BidRepository;
import com.ekhonni.backend.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Author: Asif Iqbal
 * Date: 12/22/24
 */
@Service
public class BidService extends BaseService<Bid, Long> {

    private final BidRepository bidRepository;
    private final ProductRepository productRepository;

    public BidService(ProductRepository productRepository, BidRepository bidRepository) {
        super(bidRepository);
        this.productRepository = productRepository;
        this.bidRepository = bidRepository;
    }

    @Transactional
    public void create(BidCreateDTO bidCreateDTO) {
        Product product = productRepository.findById(bidCreateDTO.productId())
                .orElseThrow(ProductNotFoundException::new);
        Bid bid = new Bid(product);
        bidRepository.save(bid);
    }
}
