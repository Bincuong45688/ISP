package com.example.isp.dto.response;

public record ChecklistItemResponse(
        Long itemId,
        String itemName,
        String unit
) {}
