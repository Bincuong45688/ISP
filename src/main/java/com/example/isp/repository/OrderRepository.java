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
    // Fix LazyInitializationException: Load voucher với JOIN FETCH
    @Query("SELECT o FROM Order o " +
           "LEFT JOIN FETCH o.voucher " +
           "WHERE o.customer.customerId = :customerId")
    List<Order> findByCustomerCustomerId(@Param("customerId") Long customerId);

    // Fix LazyInitializationException: Load voucher với JOIN FETCH
    @Query("SELECT o FROM Order o " +
           "LEFT JOIN FETCH o.voucher " +
           "WHERE o.shipper.account.username = :username AND o.status = :status")
    List<Order> findByShipperAccountUsernameAndStatus(@Param("username") String username, @Param("status") OrderStatus status);


    boolean existsByOrderCode(String orderCode);

    @Query("""
    SELECT o FROM Order o
    LEFT JOIN FETCH o.voucher
    WHERE o.customer.customerId = :customerId
""")
    List<Order> findByCustomerIdWithVoucher(@Param("customerId") Long customerId);


    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.voucher")
    List<Order> findAllWithVoucher();

}
