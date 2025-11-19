package com.shashank.comman.entity;

public class Item {
    public Item(String itemNumber, long quantity) {
        this.itemNumber = itemNumber;
        this.quantity = quantity;
    }

    public Item() {
    }

    private String itemNumber;
    private long quantity;

    @Override
    public String toString() {
        return "Item{" +
                "itemNumber='" + itemNumber + '\'' +
                ", quantity=" + quantity +
                '}';
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public long getQuantity() {
        return quantity;
    }
}
