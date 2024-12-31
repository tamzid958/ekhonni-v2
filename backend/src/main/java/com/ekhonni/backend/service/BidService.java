package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.BidCreateDTO;
import com.ekhonni.backend.dto.BidResponseDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.BidRepository;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.stereotype.Service;

@Setter
@Getter
@Service
public class BidService extends BaseService<Bid, Long> {
    private final BidRepository bidRepository;
    private final UserService userService;
    private final ProductService productService;

    public BidService(BidRepository bidRepository, UserService userRepository, ProductService productService) {
        super(bidRepository);
        this.bidRepository = bidRepository;
        this.userService = userRepository;
        this.productService = productService;
    }

    @Transactional
    public BidResponseDTO create(BidCreateDTO bidCreateDTO) {
        Product product = productService.get(bidCreateDTO.productId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found for bid"));
        User bidder = userService.get(bidCreateDTO.bidderId())
                .orElseThrow(() -> new UserNotFoundException("Bidder does not exist for bid"));
        Bid bid = new Bid(product, bidder, bidCreateDTO.amount(), bidCreateDTO.currency(), BidStatus.PENDING);
        bidRepository.save(bid);
        return BidResponseDTO.from(bidCreateDTO, bid.getId(), bid.getStatus());
    }
}
