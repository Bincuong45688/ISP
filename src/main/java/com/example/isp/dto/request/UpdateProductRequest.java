package com.example.isp.dto.request;

import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateProductRequest {
    private String productName;
    private BigDecimal price;
    private String productDescription;
    private String productImage;
    private Long categoryId;
    private Long regionId;
}
