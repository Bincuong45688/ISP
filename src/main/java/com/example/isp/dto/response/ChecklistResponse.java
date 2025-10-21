package com.example.isp.dto.response;

public record ChecklistResponse(
        Long itemId,
        String itemName,
        String itemDescription,
        String unit
) {}
