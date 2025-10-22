package com.example.isp.dto.response;

import com.example.isp.model.enums.ProductStatus;

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
        ProductStatus productStatus,
        boolean available
) {}
