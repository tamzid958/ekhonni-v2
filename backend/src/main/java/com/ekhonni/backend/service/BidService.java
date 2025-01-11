package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.bid.BidCreateDTO;
import com.ekhonni.backend.dto.bid.BidResponseDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.exception.*;
import com.ekhonni.backend.exception.bid.BidAlreadyAcceptedException;
import com.ekhonni.backend.exception.bid.BidCurrencyMismatchException;
import com.ekhonni.backend.exception.bid.BidNotFoundException;
import com.ekhonni.backend.exception.bid.InvalidBidAmountException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.BidRepository;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Setter
@Getter
@Service
public class BidService extends BaseService<Bid, Long> {

    private final BidRepository bidRepository;
    private final UserService userService;
    private final ProductService productService;

    public BidService(BidRepository bidRepository, UserService userService, ProductService productService) {
        super(bidRepository);
        this.bidRepository = bidRepository;
        this.userService = userService;
        this.productService = productService;
    }

    public <P> Page<P> getAllBidsForProduct(Long productId, Class<P> projection, Pageable pageable) {
        return bidRepository.findByProductIdAndDeletedAtIsNull(productId, projection, pageable);
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

    @Modifying
    @Transactional
    public void accept(Long id) {
        Bid bid = bidRepository.findById(id)
                .orElseThrow(() -> new BidNotFoundException("Bid not found"));
        if (bidRepository.existsByProductIdAndStatus(bid.getProduct().getId(), BidStatus.ACCEPTED)) {
            throw new BidAlreadyAcceptedException();
        }
        bid.setStatus(BidStatus.ACCEPTED);
    }

    public UUID getBidderId(Long id) {
        return bidRepository.findBidderIdById(id).orElseThrow(() -> new BidNotFoundException("Bid not found"));
    }

    public boolean isProductOwner(UUID authenticatedUserId, Long bidId) {
        Bid bid = bidRepository.findById(bidId)
                .orElseThrow(() -> new BidNotFoundException("Bid not found"));
        return bid.getProduct().getSeller().getId().equals(authenticatedUserId);
    }

}
