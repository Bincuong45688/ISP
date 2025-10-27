package com.example.isp.mapper;

import com.example.isp.dto.response.ChecklistBrief;
import com.example.isp.dto.response.ProductDetailResponse;
import com.example.isp.model.Checklist;
import com.example.isp.model.ProductDetail;

import java.util.List;

public final class ProductDetailMapper {
    private ProductDetailMapper() {}

    public static ProductDetailResponse toResponse(ProductDetail pd) {
        var p = pd.getProduct();
        List<ChecklistBrief> briefs = pd.getChecklists().stream()
                .map(ProductDetailMapper::toBrief)
                .toList();

        return new ProductDetailResponse(
                pd.getProductDetailId(),
                p.getProductId(),
                p.getProductName(),
                p.getPrice(),
                p.getCategory().getCategoryName(),
                p.getRegion().getRegionName(),
                briefs
        );
    }

    private static ChecklistBrief toBrief(Checklist c) {
        return new ChecklistBrief(
                c.getChecklistId(),
                c.getItem().getItemName(),
                c.getQuantity()
        );
    }
}
