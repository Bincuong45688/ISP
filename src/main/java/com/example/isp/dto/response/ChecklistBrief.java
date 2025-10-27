package com.example.isp.dto.response;

public record ChecklistBrief(
        Long checklistId,
        String itemName,
        Integer quantity
) {}
