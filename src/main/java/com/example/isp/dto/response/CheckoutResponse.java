package com.example.isp.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutResponse {
    private Long orderId;
    private String receiverName;
    private String email;
    private String phone;
    private String address;
    private String paymentMethod;
    private BigDecimal totalAmount;
    private String status;
    private String message;
    private LocalDateTime createdAt;
}