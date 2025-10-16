// mapper/ProductMapper.java
package com.example.isp.mapper;

import com.example.isp.dto.response.ProductResponse;
import com.example.isp.model.Product;

public class ProductMapper {
    public static ProductResponse toResponse(Product p) {
        var res = new ProductResponse();
        res.setProductId(p.getProductId());
        res.setProductName(p.getProductName());
        res.setPrice(p.getPrice());
        res.setProductDescription(p.getProductDescription());
        res.setProductImage(p.getProductImage());

        if (p.getCategory() != null) {
            res.setCategoryId(p.getCategory().getCategoryId());
            res.setCategoryName(p.getCategory().getCategoryName());
        }
        if (p.getRegion() != null) {
            res.setRegionId(p.getRegion().getRegionId());
            res.setRegionName(p.getRegion().getRegionName());
        }
        return res;
    }
}
