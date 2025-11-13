package com.example.isp.repository;

import com.example.isp.model.Payment;
import com.example.isp.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByPayosOrderCode(Long payosOrderCode);

    Optional<Payment> findTopByOrderOrderIdAndStatusOrderByIdDesc(Long orderId, PaymentStatus status);
    Optional<Payment> findByOrder_OrderId(Long orderId);

}
