package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.bid.BidCreateDTO;
import com.ekhonni.backend.dto.bid.BidResponseDTO;
import com.ekhonni.backend.dto.bid.BidUpdateDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.exception.ProductNotFoundException;
import com.ekhonni.backend.exception.UserNotFoundException;
import com.ekhonni.backend.exception.bid.*;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.BidRepository;
import com.ekhonni.backend.util.AuthUtil;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Slf4j
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

    public <P> Page<P> getAllForProduct(Long productId, Class<P> projection, Pageable pageable) {
        return bidRepository.findByProductIdAndDeletedAtIsNull(productId, projection, pageable);
    }

    public <P> Page<P> getAuditForProduct(Long productId, Class<P> projection, Pageable pageable) {
        return bidRepository.findByProductId(productId, projection, pageable);
    }

    public void checkDuplicate(@Valid BidCreateDTO bidCreateDTO) {
        UUID bidderId = AuthUtil.getAuthenticatedUser().getId();
        if (bidRepository.existsByProductIdAndBidderIdAndDeletedAtIsNull(bidCreateDTO.productId(), bidderId)) {
            throw new RuntimeException("Bid already submitted, update instead");
        }
    }

    @Modifying
    @Transactional
    public BidResponseDTO create(BidCreateDTO bidCreateDTO) {
        if (bidRepository.existsByProductIdAndStatusAndDeletedAtIsNull(bidCreateDTO.productId(), BidStatus.ACCEPTED)) {
            throw new BidAlreadyAcceptedException();
        }
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
        Bid bid = new Bid(product, bidder, bidCreateDTO.amount(), bidCreateDTO.currency(), BidStatus.PENDING);
        bidRepository.save(bid);
        return BidResponseDTO.from(bidCreateDTO, bid.getId(), bid.getStatus());
    }

    @Modifying
    @Transactional
    public void handlePreviousBid(BidCreateDTO bidCreateDTO) {
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


    @Modifying
    @Transactional
    public BidResponseDTO updateBid(Long id, BidUpdateDTO bidUpdateDTO) {
        Bid bid = get(id).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        log.info("Previous amount: {}, current amount: {}", bid.getAmount(), bidUpdateDTO.amount());
        if (bidUpdateDTO.amount() <= bid.getAmount()) {
            throw new InvalidBidAmountException("Amount must be greater than previous bid");
        }
        softDelete(id);
        return create(new BidCreateDTO(bid.getProduct().getId(), bidUpdateDTO.amount(), bid.getCurrency()));
    }

    private void validateFromPreviousBid(Bid previousBid, BidCreateDTO bidCreateDTO) {
        if (!bidCreateDTO.currency().equals(previousBid.getCurrency())) {
            throw new BidCurrencyMismatchException("Bid currency don't match");
        }
        if (bidCreateDTO.amount() <= previousBid.getAmount()) {
            throw new InvalidBidAmountException("Amount must be greater than previous bid");
        }
    }

    @Modifying
    @Transactional
    public void accept(Long id) {
        Bid bid = get(id).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        if (bidRepository.existsByProductIdAndStatusAndDeletedAtIsNull(bid.getProduct().getId(), BidStatus.ACCEPTED)) {
            throw new BidAlreadyAcceptedException();
        }
        bid.setStatus(BidStatus.ACCEPTED);
    }

    public UUID getBidderId(Long id) {
        return bidRepository.findBidderIdById(id).orElseThrow(() -> new BidNotFoundException("Bid not found"));
    }

    public boolean isProductOwner(UUID authenticatedUserId, Long bidId) {
        Bid bid = get(bidId).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        return bid.getProduct().getSeller().getId().equals(authenticatedUserId);
    }

    @Modifying
    @Transactional
    public void updateStatus(Long id, BidStatus status) {
        Bid bid = get(id).orElseThrow(() -> new BidNotFoundException("Bid not found"));
        bid.setStatus(status);
    }

    public long getCountForProduct(Long productId) {
        return bidRepository.countByProductIdAndDeletedAtIsNull(productId);
    }

    public long getAuditCountForProduct(Long productId) {
        return bidRepository.countByProductId(productId);
    }

    public <P> Page<P> getAllForUser(Class<P> projection, Pageable pageable) {
        return bidRepository.findAllByBidderId(AuthUtil.getAuthenticatedUser().getId(), projection, pageable);
    }

}
