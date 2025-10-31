package com.example.isp.dto.response;


import com.example.isp.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private String voucherCode;         // Mã voucher được áp dụng
    private BigDecimal discountAmount;  // Số tiền được giảm
    private BigDecimal totalPrice;      // Tổng tiền sau khi giảm
    private List<OrderItemResponse> items;
}
