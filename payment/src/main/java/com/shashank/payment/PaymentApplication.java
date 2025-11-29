package com.shashank.payment;

import com.shashank.comman.entity.Order;
import com.shashank.payment.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.KafkaListener;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@Slf4j
public class PaymentApplication {



    public static void main(String[] args) {
		SpringApplication.run(PaymentApplication.class, args);
	}
	private final PaymentService paymentService;
	public PaymentApplication(PaymentService paymentService) {
		this.paymentService = paymentService;
	}
	@KafkaListener(topics="${topic.name.order}")
	private void listener(Order order){
		paymentService.processPayment(order);
		log.warn("==========================================");
		log.warn("✅  payment service MESSAGE RECEIVED!");
		log.warn("Order: " + order);
		log.warn("==========================================");

		paymentService.processPayment(order);
	}

}
