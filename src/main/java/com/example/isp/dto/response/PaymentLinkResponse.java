package com.example.isp.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentLinkResponse {

    private String checkoutUrl;
    private String payosOrderCode;
    private String orderCode;
    private String amount;
}
