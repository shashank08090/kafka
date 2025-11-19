package com.shashank.order.controller;

import com.shashank.comman.entity.Order;
import com.shashank.order.entity.OrderDAO;
import com.shashank.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // CREATE - Save a single order
//    @PostMapping
//    public ResponseEntity<OrderDAO> createOrder(@RequestBody OrderDAO orderDAO) {
//        OrderDAO orderDAO = new OrderDAO();
//        OrderDAO savedOrder = orderService.save(orderDAO);
//        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
//    }

        @GetMapping("/order")
    public List<Order> createOrder(@RequestParam Long orderNum, @RequestParam  Long itemNum) {
        return this.orderService.sendOrder(orderNum,itemNum);
    }

    // CREATE - Save multiple orders
    @PostMapping("/batch")
    public ResponseEntity<Iterable<OrderDAO>> createOrders(@RequestBody Iterable<OrderDAO> orders) {
        Iterable<OrderDAO> savedOrders = orderService.saveAll(orders);
        return new ResponseEntity<>(savedOrders, HttpStatus.CREATED);
    }

    // READ - Get order by orderNumber
    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderDAO> getOrderByOrderNumber(@PathVariable String orderNumber) {
        Optional<OrderDAO> orderOptional = orderService.findByOrderNumber(orderNumber);

        return orderOptional
                .map(order -> ResponseEntity.ok(order))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }




    // Optional: Get all orders (if you add findAll in repository & service later)
     @GetMapping
     public ResponseEntity<Iterable<OrderDAO>> getAllOrders() {
         Iterable<OrderDAO> orders = orderService.getAllOrders();
         return ResponseEntity.ok(orders);
     }
}