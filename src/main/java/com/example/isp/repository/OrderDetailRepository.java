package com.example.isp.repository;

import com.example.isp.model.Order;
import com.example.isp.model.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    List<OrderDetail> findByOrderOrderId(Long orderId);

    List<OrderDetail> findByOrder(Order order);

    @Query("SELECT od FROM OrderDetail od WHERE od.order.orderCode = :orderCode")
    List<OrderDetail> findByOrderCode(@Param("orderCode") String orderCode);
}
