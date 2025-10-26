package com.example.isp.dto.response;

import com.example.isp.model.Unit;

public record ChecklistItemResponse(
        Long itemId,
        String itemName,
        Unit unit,
        Integer stockQuantity
) {}
