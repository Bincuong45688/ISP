package com.example.isp.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private String productImage;
    private BigDecimal unitPrice;
    private int quantity;
    private BigDecimal lineTotal;
    private boolean selected;
}
