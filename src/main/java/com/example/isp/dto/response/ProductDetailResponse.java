package com.example.isp.dto.response;

public record ProductDetailResponse(
        Long productDetailId,
        Long productId,
        Integer itemId,
        Integer proDetailQuantity,
        String productName,
        java.math.BigDecimal price,
        String categoryName,
        String regionName
) {}
