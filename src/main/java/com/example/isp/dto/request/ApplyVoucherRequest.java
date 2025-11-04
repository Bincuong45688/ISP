package com.example.isp.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ApplyVoucherRequest {

    @NotBlank(message = "Voucher code is required")
    private String voucherCode;

    @NotNull(message = "Order amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Order amount must be greater than 0")
    private BigDecimal orderAmount;
}
