package com.shashank.comman.entity;

import java.util.List;

public class Order {
    private String orderNumber;
    private double orderPrice;
    private String stockStatus=OrderStatus.NEW;
    private String stockStatusReason ;
    private String paymentStatus = OrderStatus.NEW;
    private List<Item> items;

    public List<Item> getItems() {
        return items;
    }
    public Order() {
    }
    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Order(String orderNumber, double orderPrice, String stockStatus, String stockStatusReason, String paymentStatus, List<Item> items, String paymentStatusReason) {
        this.orderNumber = orderNumber;
        this.orderPrice = orderPrice;
        this.stockStatus = stockStatus;
        this.stockStatusReason = stockStatusReason;
        this.paymentStatus = paymentStatus;
        this.items = items;
        this.paymentStatusReason = paymentStatusReason;
    }

    public String getPaymentStatusReason() {
        return paymentStatusReason;
    }

    public Order(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public Order(String orderNumber, double orderPrice) {
        this.orderNumber = orderNumber;
        this.orderPrice = orderPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderNumber='" + orderNumber + '\'' +
                ", orderPrice=" + orderPrice +
                ", stockStatus='" + stockStatus + '\'' +
                ", stockStatusReason='" + stockStatusReason + '\'' +
                ", paymentStatus='" + paymentStatus + '\'' +
                ", items=" + items +
                ", paymentStatusReason='" + paymentStatusReason + '\'' +
                '}';
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public String getStockStatusReason() {
        return stockStatusReason;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public void setStockStatusReason(String stockStatusReason) {
        this.stockStatusReason = stockStatusReason;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public void setPaymentStatusReason(String paymentStatusReason) {
        this.paymentStatusReason = paymentStatusReason;
    }

    private String paymentStatusReason ;

}
