package com.example.isp.model;

import com.example.isp.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
@Table(name = "orders", uniqueConstraints = {@UniqueConstraint(columnNames = "order_code")})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "order_code", nullable = false, unique = true)
    private String orderCode;

    // Liên kết với customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
    //orderCode
    @Column(name = "order_code", length = 64, unique = true)
    private String orderCode;
    // Liên kết với shipper (người giao)
    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Account shipper; // Role = SHIPPER

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;
    private String paymentMethod; // COD, BANK_TRANSFER,...

    @Column(precision = 15, scale = 2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private OrderStatus status; // PENDING, CONFIRMED, DELIVERED, CANCELED

    private LocalDateTime createdAt;

    @Column(name = "note")
    private String note;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_email")
    private String receiverEmail;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderDetail> orderDetails;
}
