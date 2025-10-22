package com.example.isp.dto.response;

import java.math.BigDecimal;

public record ProductResponse(
        Long productId,
        String productName,
        BigDecimal price,
        String productDescription,
        String productImage,
        Long categoryId,
        String categoryName,
        Long regionId,
        String regionName,
        String status
) {}
