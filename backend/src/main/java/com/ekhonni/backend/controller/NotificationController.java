package com.ekhonni.backend.controller;

import com.ekhonni.backend.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("api/v2/notifications")
public class NotificationController {

    private final NotificationService notificationService;
}
