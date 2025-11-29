package com.shashank.payment.service;

import com.shashank.comman.entity.GlobalConfigs;
import com.shashank.comman.entity.Order;
import com.shashank.comman.entity.OrderStatus;
import com.shashank.payment.producer.KafkaProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j

public class PaymentService {
    public PaymentService(GlobalConfigs globalConfigs, KafkaProducer kafkaProducer) {
        this.globalConfigs = globalConfigs;
        this.kafkaProducer = kafkaProducer;
    }

//    public void processPayment(Order order){
//        log.warn("received ordre from kafka{}",order);
//    }
    private final GlobalConfigs globalConfigs;
    private final KafkaProducer kafkaProducer;
    public Order processPayment(Order order){
        if(this.globalConfigs.getStatusPayment() == globalConfigs.SUCCESS){
            order.setPaymentStatus(OrderStatus.SUCCESS);
            order.setPaymentStatusReason("Payment Approved");
        }
        if(this.globalConfigs.getStatusPayment() == globalConfigs.FAILURE){
            order.setPaymentStatus(OrderStatus.FAILURE);
            order.setPaymentStatusReason("Payment denied ");
        }
        kafkaProducer.send(order.getOrderNumber(),order);
        return order;
    }
}
