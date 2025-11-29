package com.shashank.payment.producer;



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
    private final String paymentTopic;



    public KafkaProducer(KafkaTemplate<String, Order> kafkaTemplate, @Value("${topic.name.payment}")String orderTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.paymentTopic = orderTopic;

    }

    public void send(String key, Order payload){
        log.warn("sending to payment topic={},key={},payload = {}", paymentTopic, key,payload);
        kafkaTemplate.send(paymentTopic,key,payload);

    }
}

