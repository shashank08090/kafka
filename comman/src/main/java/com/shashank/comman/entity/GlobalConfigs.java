package com.shashank.comman.entity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:shared.properties")
public class GlobalConfigs {

    private final String itemPrefix;
    public final int SUCCESS = 1;
    public final int FAILURE = -1;
    private int statusStock;
    private int statusPayment;

    public GlobalConfigs(@Value("${order.status.stock}") int statusStock,
                         @Value("${order.status.payment}") int statusPayment,
                         @Value("${item.number.prefix}") String itemPrefix,
                         @Value("${order.status.stock:1}") int stockStatusProp,
                         @Value("${order.status.payment:1}") int paymentStatusProp) {
        this.itemPrefix = itemPrefix;
        this.statusStock = stockStatusProp;
        this.statusPayment =paymentStatusProp ;
        ;
    }

    public int getStatusStock() {
        return statusStock;
    }

    public int getStatusPayment() {
        return statusPayment;
    }

    public void setStatusStock(int statusStock) {
        this.statusStock = statusStock;
    }

    public void setStatusPayment(int statusPayment) {
        this.statusPayment = statusPayment;
    }

    public String itemPrefix() {
        return itemPrefix;
    }
}