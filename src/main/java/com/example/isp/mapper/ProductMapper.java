package com.example.isp.mapper;

import com.example.isp.dto.response.ProductResponse;
import com.example.isp.model.Product;

public class ProductMapper {
    public static ProductResponse toResponse(Product p) {
        if (p == null) return null;
        return ProductResponse.builder()
                .productId(p.getProductId())
                .productName(p.getProductName())
                .price(p.getPrice())
                .productDescription(p.getProductDescription())
                .productImage(p.getProductImage())
                .categoryName(p.getCategory() != null ? p.getCategory().getCategoryName() : null)
                .regionName(p.getRegion() != null ? p.getRegion().getRegionName() : null)
                .build();
    }
}
