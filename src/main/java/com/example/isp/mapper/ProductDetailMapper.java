package com.example.isp.mapper;

import com.example.isp.dto.response.ChecklistResponse;
import com.example.isp.dto.response.ProductDetailResponse;
import com.example.isp.model.ProductDetail;

import java.util.List;

public final class ProductDetailMapper {
    private ProductDetailMapper() {}

    public static ProductDetailResponse toResponse(ProductDetail pd) {
        var p = pd.getProduct();

        List<ChecklistResponse> responses = pd.getChecklists().stream()
                .map(ChecklistMapper::toResponse) // dùng ChecklistMapper đã có
                .toList();

        return new ProductDetailResponse(
                pd.getProductDetailId(),
                p.getProductId(),
                p.getProductName(),
                p.getPrice(),
                p.getCategory().getCategoryName(),
                p.getRegion().getRegionName(),
                responses
        );
    }
}
