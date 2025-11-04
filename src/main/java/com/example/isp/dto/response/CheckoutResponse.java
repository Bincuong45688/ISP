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
    private String orderCode;
    private String receiverName;
    private String email;
    private String phone;
    private String address;
    private String paymentMethod;
    private BigDecimal subTotal;        // Tổng tiền trước khi giảm
    private String voucherCode;         // Mã voucher được áp dụng
    private BigDecimal discountAmount;  // Số tiền được giảm
    private BigDecimal totalAmount;     // Tổng tiền sau khi giảm
    private String status;
    private String message;
    private LocalDateTime createdAt;
}