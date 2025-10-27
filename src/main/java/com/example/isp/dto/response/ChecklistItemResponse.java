package com.example.isp.dto.response;

import com.example.isp.model.enums.Unit;

public record ChecklistItemResponse(
        Long itemId,
        String itemName,
        Unit unit,
        Integer stockQuantity
) {}
