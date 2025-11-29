package com.shashank.notification.service;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    // In-memory storage for notifications (for learning purposes)
    private final Map<String, List<String>> notificationStore = new ConcurrentHashMap<>();
    private final List<String> allNotifications = new ArrayList<>();

    public void sendNotification(String orderNumber, String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String notification = String.format("[%s] Order %s: %s", timestamp, orderNumber, message);

        // Store in global list
        allNotifications.add(notification);

        // Store by order number
        notificationStore.computeIfAbsent(orderNumber, k -> new ArrayList<>()).add(notification);

        // Print to console for learning
//        System.out.println("📧 NOTIFICATION SENT: " + notification);
    }

    public List<String> getAllNotifications() {
        return new ArrayList<>(allNotifications);
    }

    public List<String> getNotificationsByOrder(String orderNumber) {
        return notificationStore.getOrDefault(orderNumber, new ArrayList<>());
    }

    public Map<String, Long> getNotificationCount() {
        Map<String, Long> count = new HashMap<>();
        count.put("total", (long) allNotifications.size());
        count.put("orders", (long) notificationStore.size());
        return count;
    }

    public void clearAllNotifications() {
        allNotifications.clear();
        notificationStore.clear();
        System.out.println("🗑️ All notifications cleared");
    }
}