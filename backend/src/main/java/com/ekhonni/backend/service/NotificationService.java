package com.ekhonni.backend.service;

import com.ekhonni.backend.repository.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;


@Setter
@Getter
@AllArgsConstructor
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;


}
