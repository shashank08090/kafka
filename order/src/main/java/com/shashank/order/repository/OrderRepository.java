package com.shashank.order.repository;

import com.shashank.order.entity.OrderDAO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository <OrderDAO,Long>{

    @Transactional
    Optional<OrderDAO> findByOrderNumber(String orderNumber);
}
