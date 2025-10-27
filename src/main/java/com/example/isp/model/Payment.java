package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payments")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    private String status;             // PENDING, PAID, FAILED
    private String payosOrderCode;     // Mã đơn hàng bên PayOS (ở đây = orderId)
    private String checkoutUrl;
    private String qrCodeUrl;

    @Column(precision = 18, scale = 2)
    private BigDecimal amount;
    private Instant createdAt;
    private Instant paidAt;

    @PrePersist
    public void prePersist() {
        createdAt = Instant.now();
    }
}
