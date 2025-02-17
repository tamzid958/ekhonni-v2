package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.bid.BidCreateDTO;
import com.ekhonni.backend.dto.bid.BidUpdateDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.enums.ProductStatus;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.exception.bid.BidAlreadyAcceptedException;
import com.ekhonni.backend.exception.bid.BidCreationException;
import com.ekhonni.backend.exception.bid.BidNotFoundException;
import com.ekhonni.backend.exception.bid.InvalidBidAmountException;
import com.ekhonni.backend.exception.user.UserNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.projection.bid.BuyerBidProjection;
import com.ekhonni.backend.repository.BidRepository;
import com.ekhonni.backend.util.AuthUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Slf4j
@Service
public class BidService extends BaseService<Bid, Long> {

    private final BidRepository bidRepository;
    private final UserService userService;
    private final ProductService productService;
    private final NotificationService notificationService;
    private final WatchlistService watchlistService;

    public BidService(BidRepository bidRepository, UserService userService, ProductService productService, NotificationService notificationService, WatchlistService watchlistService) {
        super(bidRepository);
        this.bidRepository = bidRepository;
        this.userService = userService;
        this.productService = productService;
        this.notificationService = notificationService;
        this.watchlistService =  watchlistService;
    }

    @Transactional
    public void create(BidCreateDTO bidCreateDTO) {
        Product product = productService.get(bidCreateDTO.productId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found for bid"));

        User authenticatedUser = AuthUtil.getAuthenticatedUser();
        if (product.getSeller().getId().equals(authenticatedUser.getId())) {
            throw new BidCreationException("Can not place bid for own product");
        }
        if (bidRepository.existsByProductIdAndStatusAndDeletedAtIsNull(bidCreateDTO.productId(), BidStatus.ACCEPTED)) {
            throw new BidAlreadyAcceptedException();
        }
        User bidder = userService.get(authenticatedUser.getId())
                .orElseThrow(() -> new UserNotFoundException("Bidder not found for bid"));
        Bid bid = new Bid(product, bidder, bidCreateDTO.amount(), "BDT", BidStatus.PENDING);
        bidRepository.save(bid);
        notificationService.createForNewBid(product, bidCreateDTO);
    }

    @Transactional
    public void handlePreviousBid(BidCreateDTO bidCreateDTO) {
        if (bidRepository.existsByProductIdAndStatusAndDeletedAtIsNull(bidCreateDTO.productId(), BidStatus.ACCEPTED)) {
            throw new BidAlreadyAcceptedException();
        }
        Optional<Bid> previousBidContainer = bidRepository.findByProductIdAndBidderIdAndDeletedAtIsNull(
                bidCreateDTO.productId(),
                AuthUtil.getAuthenticatedUser().getId()
        );
        if (previousBidContainer.isPresent()) {
            Bid previousBid = previousBidContainer.get();
            validateFromPreviousBid(previousBid, bidCreateDTO);
            softDelete(previousBid.getId());
        }
    }

    @Transactional
    public void updateBid(Long id, BidUpdateDTO bidUpdateDTO) {
        Bid bid = get(id).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        if (bidRepository.existsByProductIdAndStatusAndDeletedAtIsNull(bid.getProduct().getId(), BidStatus.ACCEPTED)) {
            throw new BidAlreadyAcceptedException();
        }
        if (bidUpdateDTO.amount() <= bid.getAmount()) {
            throw new InvalidBidAmountException("Amount must be greater than previous bid");
        }
        softDelete(id);
        create(new BidCreateDTO(bid.getProduct().getId(), bidUpdateDTO.amount()));
    }

    private void validateFromPreviousBid(Bid previousBid, BidCreateDTO bidCreateDTO) {
        if (bidCreateDTO.amount() <= previousBid.getAmount()) {
            throw new InvalidBidAmountException("Amount must be greater than previous bid");
        }
    }

    @Transactional
    public void accept(Long id) {
        Bid bid = get(id).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        if (bidRepository.existsByProductIdAndStatusAndDeletedAtIsNull(bid.getProduct().getId(), BidStatus.ACCEPTED)) {
            throw new BidAlreadyAcceptedException();
        }
        bid.getProduct().setStatus(ProductStatus.SOLD);
        watchlistService.removeProductFromAllUser(bid.getProduct().getId());
        bid.setStatus(BidStatus.ACCEPTED);
        notificationService.createForBidAccepted(bid);
    }

    @Transactional
    public void updateStatus(Long id, BidStatus status) {
        Bid bid = get(id).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        bid.setStatus(status);
    }

    public <P> Page<P> getAllForProduct(Long productId, Class<P> projection, Pageable pageable) {
        return bidRepository.findByProductIdAndDeletedAtIsNull(productId, projection, pageable);
    }

    public <P> Page<P> getAuditForProduct(Long productId, Class<P> projection, Pageable pageable) {
        return bidRepository.findByProductId(productId, projection, pageable);
    }

    public Double getHighestBidAmount(Long productId) {
        return bidRepository.findTopByProductIdAndDeletedAtIsNullOrderByAmountDesc(productId)
                .map(Bid::getAmount)
                .orElse(0.0);
    }

    public long getCountForProduct(Long productId) {
        return bidRepository.countByProductIdAndDeletedAtIsNull(productId);
    }

    public long getAuditCountForProduct(Long productId) {
        return bidRepository.countByProductId(productId);
    }

    public <P> Page<P> getAllForAuthenticatedBidder(Class<P> projection, Pageable pageable) {
        return bidRepository.findByBidderIdAndDeletedAtIsNull(AuthUtil.getAuthenticatedUser().getId(), projection, pageable);
    }

    public <P> Page<P> getAllForAuthenticatedSeller(Class<P> projection, Pageable pageable) {
        return bidRepository.findByProductSellerIdAndDeletedAtIsNull(AuthUtil.getAuthenticatedUser().getId(), projection, pageable);
    }

    public <P> Page<P> getAllForAuthenticatedBidderByStatus(BidStatus status, Class<P> projection, Pageable pageable) {
        return bidRepository.findByBidderIdAndStatusAndDeletedAtIsNull(
                AuthUtil.getAuthenticatedUser().getId(), status, projection, pageable);
    }

    public <P> Page<P> getAllForAuthenticatedSellerByStatus(BidStatus status, Class<P> projection, Pageable pageable) {
        return bidRepository.findByProductSellerIdAndStatusAndDeletedAtIsNull(
                AuthUtil.getAuthenticatedUser().getId(), status, projection, pageable);
    }

    public <P> Page<P> getAllForUser(UUID userId, Class<P> projection, Pageable pageable) {
        return bidRepository.findByBidderIdAndDeletedAtIsNull(userId, projection, pageable);
    }

    public BuyerBidProjection getAuthenticatedUserBidForProduct(Long productId) {
        return bidRepository.findByProductIdAndBidderIdAndDeletedAtIsNull(
                productId, AuthUtil.getAuthenticatedUser().getId(), BuyerBidProjection.class)
                .orElseThrow(() -> new BidNotFoundException("No bid submitted for this product"));
    }

    public UUID getBidderId(Long id) {
        return bidRepository.findBidderIdById(id).orElseThrow(() -> new BidNotFoundException("Bid not found"));
    }

    public boolean isProductOwner(UUID authenticatedUserId, Long bidId) {
        Bid bid = get(bidId).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        return bid.getProduct().getSeller().getId().equals(authenticatedUserId);
    }


}
