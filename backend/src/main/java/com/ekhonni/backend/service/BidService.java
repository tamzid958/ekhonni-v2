package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.BidCreateDTO;
import com.ekhonni.backend.dto.BidResponseDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.exception.*;
import com.ekhonni.backend.exception.bid.BidCurrencyMismatchException;
import com.ekhonni.backend.exception.bid.BidNotFoundException;
import com.ekhonni.backend.exception.bid.InvalidBidAmountException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.BidProjection;
import com.ekhonni.backend.repository.BidRepository;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;
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
                .orElseThrow(() -> new UserNotFoundException("Bidder not found for bid"));
        Bid bid = new Bid(product, bidder, bidCreateDTO.amount(), bidCreateDTO.currency(), BidStatus.PENDING);
        bidRepository.save(bid);
        return BidResponseDTO.from(bidCreateDTO, bid.getId(), bid.getStatus(), bid.getCreatedAt());
    }

    @Modifying
    @Transactional
    public BidResponseDTO update(Long id, BidCreateDTO bidCreateDTO) {
        Bid bid = bidRepository.findById(id)
                .orElseThrow(() -> new BidNotFoundException("Bid not found"));
        if (!bidCreateDTO.currency().equals(bid.getCurrency())) {
            throw new BidCurrencyMismatchException("Bid currency don't match");
        }
        if (bidCreateDTO.amount() <= bid.getAmount()) {
            throw new InvalidBidAmountException("Amount must be greater than previous bid");
        }
        bidRepository.softDelete(id);
        return create(bidCreateDTO);
    }

    public Page<BidProjection> getAllBidsForProduct(Long productId, Pageable pageable) {
        return bidRepository.findByProductIdAndDeletedAtIsNull(productId, BidProjection.class, pageable);
    }

    public List<BidProjection> getAllBidsForProduct(Long productId) {
        return bidRepository.findByProductIdAndDeletedAtIsNull(productId, BidProjection.class);
    }

    public UUID getBidderId(Long id) {
        return bidRepository.findBidderIdById(id).orElseThrow(() -> new BidNotFoundException("Bid not found"));
    }

}
