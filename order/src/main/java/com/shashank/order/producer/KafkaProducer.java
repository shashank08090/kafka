package com.shashank.order.producer;

import com.shashank.comman.entity.Order;
//import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class KafkaProducer {
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private final String orderTopic;
    private final String notificationTopic;


    public KafkaProducer(KafkaTemplate<String, Order> kafkaTemplate, @Value("${topic.name.order}")String orderTopic, @Value("${topic.name.notification}") String notificationTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.orderTopic = orderTopic;
        this.notificationTopic = notificationTopic;
    }

    public void sendOrder(String key, Order payload){
        log.warn("sending to order topic={},key={},payload = {}",orderTopic, key,payload);
        kafkaTemplate.send(orderTopic,key,payload);

    }
}
