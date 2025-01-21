package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.NotificationCreateRequestDTO;
import com.ekhonni.backend.dto.NotificationPreviewDTO;
import com.ekhonni.backend.dto.bid.BidCreateDTO;
import com.ekhonni.backend.enums.HTTPStatus;
import com.ekhonni.backend.enums.NotificationType;
import com.ekhonni.backend.exception.NotificationNotFoundException;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.Notification;
import com.ekhonni.backend.model.Product;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.NotificationRepository;
import com.ekhonni.backend.response.ApiResponse;
import com.ekhonni.backend.util.TimeUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private final ExecutorService executorService = Executors.newFixedThreadPool(32);


    public DeferredResult<ApiResponse<?>> handleLongPolling(UUID recipientId, LocalDateTime lastFetchTime, Pageable pageable) {
        long timeout = 30000;

        DeferredResult<ApiResponse<?>> deferredResult = new DeferredResult<>(timeout);

        executorService.submit(() -> {
            try {
                boolean hasNewNotifications = false;
                LocalDateTime pollingEndTime = LocalDateTime.now().plus(timeout, ChronoUnit.MILLIS);

                while (!hasNewNotifications && LocalDateTime.now().isBefore(pollingEndTime)) {
                    List<NotificationPreviewDTO> newNotifications = getAllNew(recipientId, lastFetchTime, pageable);
                    if (!newNotifications.isEmpty()) {
                        hasNewNotifications = true;
                        deferredResult.setResult(
                                new ApiResponse<>(HTTPStatus.OK, newNotifications)
                        );
                    } else {
                        Thread.sleep(1000);
                    }
                }

            } catch (InterruptedException e) {
                deferredResult.setErrorResult(
                        new ApiResponse<>(HTTPStatus.INTERNAL_SERVER_ERROR, "Polling interrupted")
                );
            }
        });

        deferredResult.onTimeout(() -> deferredResult.setResult(
                new ApiResponse<>(HTTPStatus.NO_CONTENT, "No new notifications available")
        ));

        return deferredResult;
    }


    public List<NotificationPreviewDTO> getAll(UUID recipientId, Pageable pageable) {
        return notificationRepository.findByRecipientIdOrRecipientIdIsNull(recipientId, pageable)
                .stream()
                .map(notifications -> new NotificationPreviewDTO(
                                notifications.getId(),
                                notifications.getMessage(),
                                timeUtils.timeAgo(notifications.getCreatedAt())
                        )
                )
                .toList();
    }

    public List<NotificationPreviewDTO> getAllNew(UUID recipientId, LocalDateTime lastFetchTime, Pageable pageable) {
        if (lastFetchTime == null) return getAll(recipientId, pageable);
        return notificationRepository.findByRecipientIdOrRecipientIdIsNullAndCreatedAtAfter(recipientId, lastFetchTime, pageable)
                .stream()
                .map(notifications -> new NotificationPreviewDTO(
                                notifications.getId(),
                                notifications.getMessage(),
                                timeUtils.timeAgo(notifications.getCreatedAt())
                        )
                )
                .toList();
    }


    public ApiResponse<?> redirect(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));

        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
        return new ApiResponse<>(HTTPStatus.OK, notification.getRedirectUrl());
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

    public ApiResponse<?> createForAllUser(NotificationCreateRequestDTO notificationCreateRequestDTO) {
        create(
                null,
                notificationCreateRequestDTO.type(),
                notificationCreateRequestDTO.message(),
                notificationCreateRequestDTO.redirectUrl()
        );

        return new ApiResponse<>(HTTPStatus.OK, "Notification created successfully");
    }

    public void createForNewBid(Product product, BidCreateDTO bidCreateDTO) {
        User seller = product.getSeller();
        NotificationType type = NotificationType.NEW_BID;
        String message = String.format(
                "Your product %s has received a new bid of %s %s",
                product.getName(),
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
                product.getName()
        );
        String redirectUrl = "http://localhost:8080/api/v2/product/" + product.getId();
        create(seller, type, message, redirectUrl);
    }

    public void createForProductRejected(Product product) {
        User seller = product.getSeller();
        NotificationType type = NotificationType.PRODUCT_REJECTED;
        String message = String.format(
                "Sorry, your product %s has been rejected by the admin.",
                product.getName()
        );
        create(seller, type, message, null);
    }

    public void createForProductDeleted(Product prouduct) {
        User seller = prouduct.getSeller();
        NotificationType type = NotificationType.PRODUCT_DELETED;
        String message = String.format(
                "Sorry, your product %s has been deleted by the admin",
                prouduct.getName()
        );
        create(seller, type, message, null);
    }

    public void createForBidAccepted(Bid bid) {
        User bidder = bid.getBidder();
        Product product = bid.getProduct();
        NotificationType type = NotificationType.BID_ACCEPTED;
        String message = String.format(
                "Congratulations! Your bid for the product %s has been accepted.",
                product.getName()
        );
        String redirectUrl = "http://localhost:8080/api/v2/product/" + product.getId();
        create(bidder, type, message, redirectUrl);
    }
}
