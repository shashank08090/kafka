package com.shashank.order.service;

import com.shashank.comman.entity.Item;
import com.shashank.comman.entity.Order;
import com.shashank.order.entity.ItemDAO;
import com.shashank.order.entity.OrderDAO;
import com.shashank.order.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository ;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDAO save(OrderDAO orderDAO){
        return orderRepository.save(orderDAO);
    }
    public List<Order> sendOrder (Long orderNum, Long itemNum){
    List<OrderDAO> orderList = generateNewOrders(orderNum,itemNum);
    orderRepository.saveAll(orderList);
    List<Order> newOrderList = ListOrderDAOtoKafka(orderList);
    return newOrderList;
    }

    public List<Order> ListOrderDAOtoKafka(List<OrderDAO> orderList){
        return orderList.stream().map(this::orderDAOtoKafka).collect(Collectors.toList());
    }


    public Order orderDAOtoKafka(OrderDAO orderDAO){
        Order orderKafka = new Order(orderDAO.getOrderNumber(),orderDAO.getOrderPrice(),orderDAO.getStockStatus() ,
        orderDAO.getStockStatusReason(),orderDAO.getPaymentStatus(),orderDAO.getItems().stream().map(itemDAO ->new Item(itemDAO.getItemNumber(),itemDAO.getQuantity())).collect(Collectors.toList()),orderDAO.getPaymentStatus());
    return orderKafka;
    }
    public List<OrderDAO> generateNewOrders(Long ordernum, Long itemNum){
        List<OrderDAO> orderList = new ArrayList<>();
        for(int i=0;i<ordernum;i++){
            OrderDAO newOrder = new OrderDAO(UUID.randomUUID().toString(),null,Math.floor(Math.random()*(1000-1+1)+1));
        newOrder.setItems(generateNewItems(itemNum,newOrder));
        orderList.add(newOrder);
        }
        return orderList;
    }
    public List<ItemDAO> generateNewItems (Long itemNumber , OrderDAO orderDAO){
        List<ItemDAO> itemList = new ArrayList<>();
        String itemName  = "iphone";
        int currentItemNum = 1;
        for(int i=0;i<itemNumber;i++){
            itemList.add(new ItemDAO(itemName+currentItemNum, 10,orderDAO));
            currentItemNum++;
        }
        return itemList;
    }
    public Optional<OrderDAO> findByOrderNumber(String orderNumber){
        return orderRepository.findByOrderNumber(orderNumber);
    }
    public <S extends OrderDAO> Iterable<S> saveAll(Iterable<S> entities){
        return orderRepository.saveAll(entities);
    }

    public List<OrderDAO> getAllOrders(){
        return orderRepository.findAll();
    }
}
