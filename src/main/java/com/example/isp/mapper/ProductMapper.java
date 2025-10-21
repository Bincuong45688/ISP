package com.example.isp.mapper;

import com.example.isp.dto.response.ProductResponse;
import com.example.isp.model.Product;

import java.util.List;
import java.util.stream.Collectors;

public final class ProductMapper {
    private ProductMapper() {}

    public static ProductResponse toResponse(Product p) {
        if (p == null) return null;

        var c = p.getCategory();
        var r = p.getRegion();
        Long categoryId = (c != null) ? c.getCategoryId() : null;
        String categoryName = (c != null) ? c.getCategoryName() : null;
        Long regionId = (r != null) ? r.getRegionId() : null;
        String regionName = (r != null) ? r.getRegionName() : null;

        return new ProductResponse(
                p.getProductId(),
                p.getProductName(),
                p.getPrice(),
                p.getProductDescription(),
                p.getProductImage(),
                categoryId, categoryName,
                regionId, regionName
        );
    }

    // === Thêm mới: tiện ích chuyển danh sách ===
    public static List<ProductResponse> toResponseList(List<Product> products) {
        return products.stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }
}
