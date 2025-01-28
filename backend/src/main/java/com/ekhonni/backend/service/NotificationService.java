package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.bid.BidCreateDTO;
import com.ekhonni.backend.dto.NotificationPreviewDTO;
import com.ekhonni.backend.enums.NotificationType;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Notification;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.NotificationRepository;
import com.ekhonni.backend.util.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 01/09/26
 */

@Setter
@Getter
@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final TimeUtils timeUtils;

    public List<NotificationPreviewDTO> getAll(UUID recipientId, Pageable pageable) {
        return notificationRepository.findByRecipientId(recipientId, pageable)
                .stream()
                .map(notifications -> new NotificationPreviewDTO(
                                notifications.getMessage(),
                                timeUtils.timeAgo(notifications.getCreatedAt())
                        )
                )
                .toList();
    }

    public List<NotificationPreviewDTO> getAllNew(UUID recipientId, LocalDateTime lastFetchTime, Pageable pageable) {
        if (lastFetchTime == null) return getAll(recipientId, pageable);
        return notificationRepository.findByRecipientIdAndCreatedAtAfter(recipientId, lastFetchTime, pageable)
                .stream()
                .map(notifications -> new NotificationPreviewDTO(
                                notifications.getMessage(),
                                timeUtils.timeAgo(notifications.getCreatedAt())
                        )
                )
                .toList();
    }

    @Transactional
    public void delete(UUID userId, Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new RuntimeException("Notification not found"));

        notificationRepository.delete(notification);
    }


    public void create(User recipient, NotificationType type, String message, String redirectUrl) {
        Notification notification = new Notification(
                recipient,
                type,
                message,
                null,
                redirectUrl
        );
        notificationRepository.save(notification);
    }

    public void createForNewBid(Product product, BidCreateDTO bidCreateDTO) {
        User seller = product.getSeller();
        NotificationType type = NotificationType.NEW_BID;
        String message = String.format(
                "Your product %s has received a new bid of %s %s",
                product.getTitle(),
                bidCreateDTO.amount(),
                bidCreateDTO.currency()
        );
        String redirectUrl = "http://localhost:8080/api/v2/product/" + product.getId();
        create(seller, type, message, redirectUrl);
    }

    public void createForProductAccepted(Product product) {
        User seller = product.getSeller();
        NotificationType type = NotificationType.PRODUCT_ACCEPTED;
        String message = String.format(
                "Your product %s has been accepted by the admin.",
                product.getTitle()
        );
        String redirectUrl = "http://localhost:8080/api/v2/product/" + product.getId();
        create(seller, type, message, redirectUrl);
    }

    public void createForProductRejected(Product product) {
        User seller = product.getSeller();
        NotificationType type = NotificationType.PRODUCT_REJECTED;
        String message = String.format(
                "Sorry, your product %s has been rejected by the admin.",
                product.getTitle()
        );
        create(seller, type, message, null);
    }

    public void createForProductDeleted(Product prouduct) {
        User seller = prouduct.getSeller();
        NotificationType type = NotificationType.PRODUCT_DELETED;
        String message = String.format(
                "Sorry, your product %s has been deleted by the admin",
                prouduct.getTitle()
        );
        create(seller, type, message, null);
    }

    public void createForBidAccepted(Bid bid) {
        User bidder = bid.getBidder();
        Product product = bid.getProduct();
        NotificationType type = NotificationType.BID_ACCEPTED;
        String message = String.format(
                "Congratulations! Your bid for the product %s has been accepted.",
                product.getTitle()
        );
        String redirectUrl = "http://localhost:8080/api/v2/product/" + product.getId();
        create(bidder, type, message, redirectUrl);
    }
}
