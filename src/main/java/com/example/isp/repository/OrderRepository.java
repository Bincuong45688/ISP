package com.example.isp.repository;

import com.example.isp.model.Order;
import com.example.isp.model.enums.OrderStatus;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerCustomerId(Long customerId);
    List<Order> findByShipperUsername(String username);
    List<Order> findByShipperUsernameAndStatus(String username, OrderStatus status);

    boolean existsByOrderCode(String orderCode);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.orderId = :id")
    Optional<Order> findByIdForUpdate(@Param("id") Long id);
}
