package com.example.isp.repository;

import com.example.isp.model.Order;
import com.example.isp.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerCustomerId(Long customerId);
    List<Order> findByShipperUsername(String username);
    List<Order> findByShipperUsernameAndStatus(String username, OrderStatus status);
}
