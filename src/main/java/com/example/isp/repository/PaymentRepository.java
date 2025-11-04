package com.example.isp.repository;

import com.example.isp.model.Payment;
import com.example.isp.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    /** Tìm Payment theo payos_order_code (duy nhất) */
    Optional<Payment> findByPayosOrderCode(Long payosOrderCode);

    /** Lấy Payment mới nhất trong hệ thống (để sinh order_code mới) */
    Optional<Payment> findTopByOrderByIdDesc();

    /** Lấy Payment mới nhất của 1 orderId theo status */
    Optional<Payment> findTopByOrder_OrderIdAndStatusOrderByIdDesc(Long orderId, PaymentStatus status);
}
