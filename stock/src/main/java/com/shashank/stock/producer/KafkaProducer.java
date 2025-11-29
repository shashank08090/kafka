package com.shashank.stock.producer;
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
    private final String stockTopic;



    public KafkaProducer(KafkaTemplate<String, Order> kafkaTemplate, @Value("${topic.name.payment}")String stockTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.stockTopic = stockTopic;

    }

    public void sendOrder(String key, Order payload){
        log.warn("sending to payment topic={},key={},payload = {}", stockTopic, key,payload);
        kafkaTemplate.send(stockTopic,key,payload);

    }
}


