package com.example.isp.dto.request;


import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private String note;
    private BigDecimal totalAmount;
}
