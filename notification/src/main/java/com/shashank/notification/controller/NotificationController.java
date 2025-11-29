package com.shashank.notification.controller;

import com.shashank.notification.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllNotifications() {
        List<String> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/order/{orderNumber}")
    public ResponseEntity<List<String>> getNotificationsByOrder(@PathVariable String orderNumber) {
        List<String> notifications = notificationService.getNotificationsByOrder(orderNumber);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getNotificationCount() {
        Map<String, Long> count = notificationService.getNotificationCount();
        return ResponseEntity.ok(count);
    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearAllNotifications() {
        notificationService.clearAllNotifications();
        return ResponseEntity.ok("All notifications cleared");
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Notification service is running");
    }
}