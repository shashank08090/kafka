package com.shashank.order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shashank.comman.entity.Item;
import jakarta.persistence.*;

@Entity
public class ItemDAO extends Item {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @ManyToOne
    @JoinColumn(name="order_id")
    @JsonBackReference
    private OrderDAO order;
    public void setId(Long id) {
        Id = id;
    }

    public ItemDAO(){

    }


    public ItemDAO( String itemNumber, long quantity , OrderDAO orderDAO) {

        super(itemNumber,quantity);
        this.itemNumber = itemNumber;
        this.quantity = quantity;
        this.order = orderDAO;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Long getId() {
        return Id;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public long getQuantity() {
        return quantity;
    }

    private String itemNumber;
    private long quantity;
}
