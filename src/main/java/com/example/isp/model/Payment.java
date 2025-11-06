package com.example.isp.model;

import com.example.isp.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "payments")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String provider;              // "PAYOS"
    private String transactionId;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PaymentStatus status;         // PENDING, SUCCESS, FAILED

    @Column(name = "payos_order_code", unique = true, length = 255)
    private Long payosOrderCode;  // orderCode gửi cho PayOS (trùng với OrderCode)

    @Column(name = "checkout_url", length = 1024)
    private String checkoutUrl;           // lưu để tái sử dụng link nếu cần

    private OffsetDateTime createdAt;
    private OffsetDateTime paidAt;
}
