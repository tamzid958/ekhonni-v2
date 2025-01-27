package com.ekhonni.backend.service;

import com.ekhonni.backend.dto.NotificationPreviewDTO;
import com.ekhonni.backend.projection.NotificationPreviewProjection;
import com.ekhonni.backend.repository.NotificationRepository;
import com.ekhonni.backend.util.TimeUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @Mock
    private TimeUtils timeUtils;

    @InjectMocks
    private NotificationService notificationService;

    private UUID userId;
    private Pageable pageable;
    private NotificationPreviewProjection notificationProjection1;
    private NotificationPreviewProjection notificationProjection2;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();

        notificationProjection1 = mock(NotificationPreviewProjection.class);
        when(notificationProjection1.getMessage()).thenReturn("5% discount!!!");
        when(notificationProjection1.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(notificationProjection1.getId()).thenReturn(1L);


        notificationProjection2 = mock(NotificationPreviewProjection.class);
        when(notificationProjection2.getMessage()).thenReturn("New Product Available");
        when(notificationProjection2.getCreatedAt()).thenReturn(LocalDateTime.now());
        when(notificationProjection2.getId()).thenReturn(2L);

        pageable = Pageable.unpaged();
    }

    @Test
    void testGetAll() {
        when(notificationRepository.findByRecipientIdOrRecipientIdIsNull(eq(userId), eq(pageable)))
                .thenReturn(List.of(notificationProjection1, notificationProjection2));

        when(timeUtils.timeAgo(any(LocalDateTime.class))).thenReturn("5 minutes ago");

        List<NotificationPreviewDTO> result = notificationService.getAll(userId, pageable);

        assertEquals(2, result.size());
    }

    @Test
    void testGetAllNew_withLastFetchTime(){
        LocalDateTime lastFetchTime = LocalDateTime.now().minusMinutes(5);

        when(notificationRepository.findByRecipientIdOrRecipientIdIsNullAndCreatedAtAfter(eq(userId), eq(lastFetchTime), eq(pageable)))
                .thenReturn(List.of(notificationProjection1));

        when(timeUtils.timeAgo(any(LocalDateTime.class))).thenReturn("5 minutes ago");

        List<NotificationPreviewDTO> result = notificationService.getAllNew(userId, lastFetchTime, pageable);

        assertEquals(1, result.size());
        assertEquals("5% discount!!!", result.get(0).message());
    }


}
