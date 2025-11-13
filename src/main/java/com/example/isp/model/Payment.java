package com.example.isp.model;

import com.example.isp.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private String provider; // PAYOS, CASH…

    private String transactionId;

    @Column(precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private PaymentStatus status;

    @Column(name = "payos_order_code", length = 255)
    private Long payosOrderCode;

    @Column(name = "checkout_url", length = 1024)
    private String checkoutUrl;

    @Column(name = "qr_code", length = 2048)
    private String qrCode;   //

    @Column(name = "payment_link_id", length = 255)
    private String paymentLinkId;  //

    private OffsetDateTime createdAt;
    private OffsetDateTime paidAt;
}
