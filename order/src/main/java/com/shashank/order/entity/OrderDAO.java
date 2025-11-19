package com.shashank.order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shashank.comman.entity.OrderStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class OrderDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<ItemDAO> items ;
    private double orderPrice;
    private String orderNumber;

    public Long getId() {
        return id;
    }

    public OrderDAO() {
    }

    public OrderDAO(String orderNumber, List<ItemDAO> items, double orderPrice) {
        this.orderNumber = orderNumber;
        this.items = items;
        this.orderPrice = orderPrice;
    }

    public OrderDAO(Long id, List<ItemDAO> items, double orderPrice, String orderNumber, String stockStatus, String stockStatusReason, String paymentStatus) {
        this.id = id;
        this.items = items;
        this.orderPrice = orderPrice;
        this.orderNumber = orderNumber;
        this.stockStatus = stockStatus;
        this.stockStatusReason = stockStatusReason;
        this.paymentStatus = paymentStatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ItemDAO> getItems() {
        return items;
    }

    public void setItems(List<ItemDAO> items) {
        this.items = items;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStockStatus() {
        return stockStatus;
    }

    public void setStockStatus(String stockStatus) {
        this.stockStatus = stockStatus;
    }

    public String getStockStatusReason() {
        return stockStatusReason;
    }

    public void setStockStatusReason(String stockStatusReason) {
        this.stockStatusReason = stockStatusReason;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    private String stockStatus= OrderStatus.NEW;
    private String stockStatusReason ;
    private String paymentStatus = OrderStatus.NEW;

}
