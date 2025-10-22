package com.example.isp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckoutRequest {
    private Long cartId;
    private String address;
    private String phone;
    private String paymentMethod; // COD, BANK_TRANSFER,...
}
