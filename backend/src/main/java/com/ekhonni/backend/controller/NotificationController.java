package com.ekhonni.backend.controller;

import com.ekhonni.backend.service.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author: Safayet Rafi
 * Date: 12/31/24
 */

@AllArgsConstructor
@RestController
@RequestMapping("api/v2/notifications")
public class NotificationController {

    private final NotificationService notificationService;
}
