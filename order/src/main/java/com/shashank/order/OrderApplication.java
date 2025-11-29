package com.shashank.order;

import com.shashank.order.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class OrderApplication {



    public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
	private final String kafkaTopicStock;
	private final String kafkaTopicPayment;
	private final String kafkaTopicNotification;
	private final Long joinWindow;
	private final OrderService orderService;

	public OrderApplication(@Value("${topic.name.stock}")String kafkaTopicStock,@Value("${topic.name.payment}") String kafkaTopicPayment, @Value("${topic.name.notification}")String kafkaTopicNotification,@Value("${kafka.join.window.duration.ms}") Long joinWindow, OrderService orderService) {
		this.kafkaTopicStock = kafkaTopicStock;
		this.kafkaTopicPayment = kafkaTopicPayment;
		this.kafkaTopicNotification = kafkaTopicNotification;
		this.joinWindow = joinWindow;
		this.orderService = orderService;
		System.setProperty("spring.kafka.streams.state-dir","/temp/kafka-stream"+ UUID.randomUUID());
	}
}
