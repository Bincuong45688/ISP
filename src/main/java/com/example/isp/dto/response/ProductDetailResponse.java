package com.example.isp.dto.response;

import java.math.BigDecimal;
import java.util.List;

public record ProductDetailResponse(
        Long productDetailId,
        Long productId,
        String productName,
        BigDecimal price,
        String categoryName,
        String regionName,
        List<ChecklistResponse> checklists
) {}
