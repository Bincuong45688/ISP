package com.example.isp.mapper;

import com.example.isp.dto.response.ProductDetailResponse;
import com.example.isp.model.ProductDetail;

public final class ProductDetailMapper {

    private ProductDetailMapper() {}

    public static ProductDetailResponse toResponse(ProductDetail pd) {
        var p = pd.getProduct(); // p là Product

        return new ProductDetailResponse(
                // ----- thuộc ProductDetail -----
                pd.getProductDetailId(),
                // ----- thuộc Product -----
                p.getProductId(),
                // ----- thuộc ProductDetail -----
                pd.getItemId(),
                pd.getProDetailQuantity(),
                // ----- thuộc Product -----
                p.getProductName(),
                p.getPrice(),
                p.getCategory().getCategoryName(),
                p.getRegion().getRegionName()
        );
    }
}
