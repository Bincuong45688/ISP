package com.example.isp.repository;

import com.example.isp.model.Payment;
import com.example.isp.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findTopByOrder_OrderIdOrderByIdDesc(Long orderId);

    Optional<Payment> findTopByOrder_OrderIdAndStatusOrderByIdDesc(Long orderId, PaymentStatus status);

    Optional<Payment> findByPayosOrderCode(Long payosOrderCode);

    long countByOrder_OrderId(Long orderId);
}
