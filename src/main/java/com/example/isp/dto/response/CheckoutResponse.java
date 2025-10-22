package com.example.isp.dto.response;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutResponse {
    private Long orderId;
    private BigDecimal totalAmount;
    private String status;
    private String paymentMethod;
    private String message;
}