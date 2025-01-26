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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {
  
}