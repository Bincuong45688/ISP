package com.example.isp.dto.response;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartResponse {
    private Long cartId;
    private String cartStatus;
    private Long customerId;
    private String customerName;
    private List<CartItemResponse> items;
    private int totalItems;
    private BigDecimal subTotal;
    
    // Thông tin voucher
    private String voucherCode;
    private BigDecimal discountAmount;
    private BigDecimal finalAmount; // Số tiền sau khi trừ voucher
    
    private String currency;
}

