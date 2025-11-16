package com.example.isp.repository;

import com.example.isp.model.Order;
import com.example.isp.model.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT o FROM Order o
        LEFT JOIN FETCH o.voucher
        WHERE o.customer.customerId = :customerId
    """)
    List<Order> findByCustomerCustomerId(@Param("customerId") Long customerId);


    @Query("""
        SELECT o FROM Order o
        LEFT JOIN FETCH o.orderDetails od
        LEFT JOIN FETCH od.product p
        WHERE o.customer.customerId = :customerId
        AND o.status = :status
    """)
    List<Order> findByCustomerCustomerIdAndStatus(
            @Param("customerId") Long customerId,
            @Param("status") OrderStatus status
    );


    @Query("""
        SELECT o FROM Order o
        LEFT JOIN FETCH o.voucher
        WHERE o.shipper.account.username = :username
        AND o.status = :status
    """)
    List<Order> findByShipperAccountUsernameAndStatus(
            @Param("username") String username,
            @Param("status") OrderStatus status
    );


    boolean existsByOrderCode(String orderCode);


    @Query("""
        SELECT o FROM Order o
        LEFT JOIN FETCH o.voucher
        WHERE o.customer.customerId = :customerId
    """)
    List<Order> findByCustomerIdWithVoucher(@Param("customerId") Long customerId);


    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.voucher")
    List<Order> findAllWithVoucher();

    // ⭐⭐⭐ FIX LAZY FOR FEEDBACK ⭐⭐⭐
    @Query("""
           SELECT DISTINCT o FROM Order o
           LEFT JOIN FETCH o.orderDetails od
           LEFT JOIN FETCH od.product
           WHERE o.orderId = :orderId
           """)
    Optional<Order> findByIdWithDetails(@Param("orderId") Long orderId);
}
