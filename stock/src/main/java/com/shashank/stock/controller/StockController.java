package com.shashank.stock.controller;

import com.shashank.comman.entity.GlobalConfigs;
import com.shashank.comman.entity.Item;
import com.shashank.stock.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stock")
public class StockController {

    private final StockService stockService;
    private final GlobalConfigs globalConfigs;
    public record StockSimStatus(boolean success) {}
    public StockController(StockService stockService, GlobalConfigs globalConfigs) {
        this.stockService = stockService;
        this.globalConfigs = globalConfigs;
    }

    /**
     * Get all items in stock
     */
    @GetMapping("/items")
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> items = stockService.getAllItems();
        return ResponseEntity.ok(items);
    }

    /**
     * Get a specific item by item number
     */
    @GetMapping("/items/{itemNumber}")
    public ResponseEntity<Item> getItemByNumber(@PathVariable String itemNumber) {
        Item item = stockService.getItemByNumber(itemNumber);
        if (item != null) {
            return ResponseEntity.ok(item);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Check if items are available in stock
     */
    @PostMapping("/check-availability")
    public ResponseEntity<Map<String, Object>> checkAvailability(@RequestBody List<Item> items) {
        Map<String, Object> result = stockService.checkAvailability(items);
        return ResponseEntity.ok(result);
    }

    /**
     * Add new item to stock
     */
    @PostMapping("/items")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item addedItem = stockService.addItem(item);
        return ResponseEntity.ok(addedItem);
    }

    /**
     * Update item quantity
     */
    @PutMapping("/items/{itemNumber}/quantity")
    public ResponseEntity<Item> updateQuantity(
            @PathVariable String itemNumber,
            @RequestParam int quantity
    ) {
        Item updatedItem = stockService.updateQuantity(itemNumber, quantity);
        if (updatedItem != null) {
            return ResponseEntity.ok(updatedItem);
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Delete item from stock
     */
    @DeleteMapping("/items/{itemNumber}")
    public ResponseEntity<String> deleteItem(@PathVariable String itemNumber) {
        boolean deleted = stockService.deleteItem(itemNumber);
        if (deleted) {
            return ResponseEntity.ok("Item deleted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Get stock statistics
     */
    @GetMapping("/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics() {
        Map<String, Object> stats = stockService.getStatistics();
        return ResponseEntity.ok(stats);
    }

    /**
     * Get low stock items
     */
    @GetMapping("/low-stock")
    public ResponseEntity<List<Item>> getLowStockItems(@RequestParam(defaultValue = "10") int threshold) {
        List<Item> lowStockItems = stockService.getLowStockItems(threshold);
        return ResponseEntity.ok(lowStockItems);
    }

    /**
     * Reserve items for an order
     */
    @PostMapping("/reserve")
    public ResponseEntity<Map<String, Object>> reserveItems(@RequestBody Map<String, Object> request) {
        String orderNumber = (String) request.get("orderNumber");
        List<Item> items = (List<Item>) request.get("items");

        Map<String, Object> result = stockService.reserveItems(orderNumber, items);
        return ResponseEntity.ok(result);
    }

    /**
     * Release reserved items (in case of order cancellation)
     */
    @PostMapping("/release/{orderNumber}")
    public ResponseEntity<String> releaseReservedItems(@PathVariable String orderNumber) {
        boolean released = stockService.releaseReservedItems(orderNumber);
        if (released) {
            return ResponseEntity.ok("Items released successfully");
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * Health check endpoint
     */
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Stock service is running");
    }

    @GetMapping("/status")
    @Operation(
            summary = "Get current payment simulation status",
            description = "Returns whether payment service is currently in success or failure mode."
    )

    public ResponseEntity<StockSimStatus> getStockStatus() {
        boolean success=globalConfigs.getStatusStock()==this.globalConfigs.SUCCESS;
        return ResponseEntity.ok(new StockSimStatus(success));
    }

    @PutMapping("/status")
    @Operation(
            summary = "Set payment simulation status",
            description = """
                    Controls whether the payment service responds with success or failure
                    for all new orders. Does not require service restart.
                    """
    )

    public ResponseEntity<StockSimStatus> setStockStatus(
            @Parameter(
                    description = "true = always approve payments, false = always fail payments",
                    example = "true"
            )
            @RequestParam("success") boolean success
    ) {
        globalConfigs.setStatusStock(success?this.globalConfigs.SUCCESS:this.globalConfigs.FAILURE);
        return ResponseEntity.ok(new StockSimStatus(success));
    }
}