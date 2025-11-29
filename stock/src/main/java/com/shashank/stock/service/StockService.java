package com.shashank.stock.service;

import com.shashank.comman.entity.Item;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class StockService {

    // In-memory storage for items (for learning purposes)
    private final Map<String, Item> stockInventory = new ConcurrentHashMap<>();

    // Track reserved items by order number
    private final Map<String, List<Item>> reservedItems = new ConcurrentHashMap<>();

    public StockService() {
        // Initialize with some sample data
        initializeSampleData();
    }

    private void initializeSampleData() {
        addItem(new Item("ITEM001", "Laptop", 50, 45000.0));
        addItem(new Item("ITEM002", "Mouse", 200, 500.0));
        addItem(new Item("ITEM003", "Keyboard", 150, 1200.0));
        addItem(new Item("ITEM004", "Monitor", 75, 15000.0));
        addItem(new Item("ITEM005", "Headphones", 100, 2000.0));
    }

    public List<Item> getAllItems() {
        return new ArrayList<>(stockInventory.values());
    }

    public Item getItemByNumber(String itemNumber) {
        return stockInventory.get(itemNumber);
    }

    public Map<String, Object> checkAvailability(List<Item> items) {
        Map<String, Object> result = new HashMap<>();
        List<String> unavailableItems = new ArrayList<>();
        boolean allAvailable = true;

        for (Item requestedItem : items) {
            Item stockItem = stockInventory.get(requestedItem.getItemNumber());

            if (stockItem == null) {
                unavailableItems.add(requestedItem.getItemNumber() + " (not found)");
                allAvailable = false;
            } else if (stockItem.getQuantity() < requestedItem.getQuantity()) {
                unavailableItems.add(requestedItem.getItemNumber() +
                        " (requested: " + requestedItem.getQuantity() +
                        ", available: " + stockItem.getQuantity() + ")");
                allAvailable = false;
            }
        }

        result.put("available", allAvailable);
        result.put("unavailableItems", unavailableItems);
        result.put("message", allAvailable ? "All items available" : "Some items unavailable");

        return result;
    }

    public Item addItem(Item item) {
        stockInventory.put(item.getItemNumber(), item);
        System.out.println("✅ Item added to stock: " + item);
        return item;
    }

    public Item updateQuantity(String itemNumber, int quantity) {
        Item item = stockInventory.get(itemNumber);
        if (item != null) {
            item.setQuantity(quantity);
            System.out.println("📦 Stock updated for " + itemNumber + ": " + quantity);
            return item;
        }
        return null;
    }

    public boolean deleteItem(String itemNumber) {
        Item removed = stockInventory.remove(itemNumber);
        if (removed != null) {
            System.out.println("🗑️ Item removed from stock: " + itemNumber);
            return true;
        }
        return false;
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();

        int totalItems = stockInventory.size();
        int totalQuantity = stockInventory.values().stream()
                .mapToInt(Item::getQuantity)
                .sum();
        double totalValue = stockInventory.values().stream()
                .mapToDouble(item -> item.getQuantity() * item.getPrice())
                .sum();

        stats.put("totalItems", totalItems);
        stats.put("totalQuantity", totalQuantity);
        stats.put("totalValue", totalValue);
        stats.put("lowStockItems", getLowStockItems(10).size());

        return stats;
    }

    public List<Item> getLowStockItems(int threshold) {
        return stockInventory.values().stream()
                .filter(item -> item.getQuantity() < threshold)
                .collect(Collectors.toList());
    }

    public Map<String, Object> reserveItems(String orderNumber, List<Item> items) {
        Map<String, Object> result = new HashMap<>();

        // First check if all items are available
        Map<String, Object> availability = checkAvailability(items);

        if (!(Boolean) availability.get("available")) {
            result.put("success", false);
            result.put("message", "Cannot reserve - items not available");
            result.put("details", availability);
            return result;
        }

        // Reserve the items by reducing quantity
        List<Item> reserved = new ArrayList<>();
        for (Item requestedItem : items) {
            Item stockItem = stockInventory.get(requestedItem.getItemNumber());
            if (stockItem != null) {
                int newQuantity = stockItem.getQuantity() - requestedItem.getQuantity();
                stockItem.setQuantity(newQuantity);
                reserved.add(new Item(
                        requestedItem.getItemNumber(),
                        requestedItem.getItemName(),
                        requestedItem.getQuantity(),
                        requestedItem.getPrice()
                ));
            }
        }

        reservedItems.put(orderNumber, reserved);

        result.put("success", true);
        result.put("message", "Items reserved successfully");
        result.put("orderNumber", orderNumber);
        result.put("reservedItems", reserved);

        System.out.println("🔒 Items reserved for order: " + orderNumber);

        return result;
    }

    public boolean releaseReservedItems(String orderNumber) {
        List<Item> reserved = reservedItems.get(orderNumber);

        if (reserved == null) {
            return false;
        }

        // Add quantities back to stock
        for (Item item : reserved) {
            Item stockItem = stockInventory.get(item.getItemNumber());
            if (stockItem != null) {
                int newQuantity = stockItem.getQuantity() + item.getQuantity();
                stockItem.setQuantity(newQuantity);
            }
        }

        reservedItems.remove(orderNumber);
        System.out.println("🔓 Items released for order: " + orderNumber);

        return true;
    }

    public boolean reduceStock(List<Item> items) {
        for (Item requestedItem : items) {
            Item stockItem = stockInventory.get(requestedItem.getItemNumber());
            if (stockItem != null && stockItem.getQuantity() >= requestedItem.getQuantity()) {
                int newQuantity = stockItem.getQuantity() - requestedItem.getQuantity();
                stockItem.setQuantity(newQuantity);
                System.out.println("📉 Stock reduced for " + requestedItem.getItemNumber() +
                        ": " + stockItem.getQuantity());
            } else {
                return false;
            }
        }
        return true;
    }
}