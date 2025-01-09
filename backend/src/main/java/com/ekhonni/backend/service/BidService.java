package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.BidCreateDTO;
import com.ekhonni.backend.dto.BidResponseDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.exception.BidNotFoundException;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.Transaction;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.BidRepository;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
        User authenticatedUser = AuthUtil.getAuthenticatedUser();
        User bidder = userService.get(authenticatedUser.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found for bid"));
        Bid bid = new Bid(product, bidder, bidCreateDTO.amount(), bidCreateDTO.currency(), BidStatus.PENDING);
        bidRepository.save(bid);
        return BidResponseDTO.from(bidCreateDTO, bid.getId(), bid.getStatus());
    }

    public UUID getBidderId(Long id) {
        return bidRepository.findBidderIdById(id)
                .orElseThrow(() -> new BidNotFoundException("Bid not found"));
    }

}
