package com.example.isp.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TopSellingProductResponse {
    private Long productId;
    private String productName;
    private Long categoryId;
    private String categoryName;
    private Long quantity;
    private BigDecimal revenue;
    private String imageUrl;
    private BigDecimal price;
    private String description;
    private LocalDateTime createdAt;
}
