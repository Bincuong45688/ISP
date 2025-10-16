package com.example.isp.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductResponse {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private String productDescription;
    private String productImage;
    private String categoryName;
    private String regionName;
}
