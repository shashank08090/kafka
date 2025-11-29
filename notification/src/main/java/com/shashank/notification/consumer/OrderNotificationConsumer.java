package com.shashank.notification.consumer;

import com.shashank.comman.entity.Order;
import com.shashank.notification.service.NotificationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderNotificationConsumer {

    private final NotificationService notificationService;

    public OrderNotificationConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(
            topics = "${topic.name.order}",
            groupId = "notification-service"
    )
    public void consumeOrderCreated(Order order) {
        System.out.println("===========================================");
        System.out.println("📦 New Order Received in Notification Service");
        System.out.println("Order Number: " + order.getOrderNumber());
        System.out.println("Order Price: ₹" + order.getOrderPrice());
        System.out.println("===========================================");

        String message = String.format("Order created with price ₹%.2f", order.getOrderPrice());
        notificationService.sendNotification(order.getOrderNumber(), message);
    }

    @KafkaListener(
            topics = "${topic.name.payment}",
            groupId = "notification-service"
    )
    public void consumePaymentUpdate(Order order) {
        System.out.println("===========================================");
        System.out.println("💳 Payment Update Received");
        System.out.println("Order Number: " + order.getOrderNumber());
        System.out.println("Payment Status: " + order.getPaymentStatus());
        System.out.println("===========================================");

        String message = String.format("Payment status updated to: %s", order.getPaymentStatus());
        notificationService.sendNotification(order.getOrderNumber(), message);
    }

    @KafkaListener(
            topics = "${topic.name.stock}",
            groupId = "notification-service"
    )
    public void consumeStockUpdate(Order order) {
        System.out.println("===========================================");
        System.out.println("📦 Stock Update Received");
        System.out.println("Order Number: " + order.getOrderNumber());
        System.out.println("Stock Status: " + order.getStockStatus());
        System.out.println("===========================================");

        String message = String.format("Stock status updated to: %s", order.getStockStatus());
        notificationService.sendNotification(order.getOrderNumber(), message);
    }
}