package com.shashank.notification.service;

import com.shashank.notification.entity.NotificationEntity;
import com.shashank.notification.repository.NotificationRepository;
import org.springframework.stereotype.Service;

@Service
public class NotificationService29Nov {

    private final NotificationRepository repository;

    public NotificationService29Nov(NotificationRepository repository) {
        this.repository = repository;
    }

    public NotificationEntity sendNotification(String event) {
        NotificationEntity notification = new NotificationEntity(event);
        notification.x = "mycompanynameCapg";
        System.out.println(notification.x);
        NotificationEntity saved = repository.save(notification);
        System.out.println("Notification sent: " + event);
        return saved;
    }
}