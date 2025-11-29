package com.shashank.notification.controller;

import com.shashank.notification.entity.NotificationEntity;
import com.shashank.notification.service.NotificationService29Nov;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController29Nov {

    private final NotificationService29Nov notificationService;

    public NotificationController29Nov(NotificationService29Nov notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/send")
    public NotificationEntity send(@RequestParam String event) {
        return notificationService.sendNotification(event);
    }
}