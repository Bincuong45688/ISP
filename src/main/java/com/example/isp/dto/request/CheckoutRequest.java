package com.example.isp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutRequest {
    private String fullName;        // Họ và tên người nhận
    private String email;           // Email xác nhận đơn hàng
    private String phone;           // SĐT người nhận
    private String address;         // Địa chỉ giao hàng
    private String paymentMethod;   // COD / BANK
    private String note;            // Ghi chú thêm (nếu có)
}
