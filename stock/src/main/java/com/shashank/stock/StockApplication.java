package com.shashank.stock;

import com.shashank.comman.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class StockApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockApplication.class, args);
	}
	@KafkaListener(topics="${topic.name.order}")
	private void listener(Order order){
//		paymentService.processPayment(order);
		log.warn("==========================================");
		log.warn("✅  Stock Service-MESSAGE RECEIVED!");
		log.warn("Order: " + order);
		log.warn("==========================================");
	}
}
