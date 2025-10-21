package com.example.isp.dto.response;

public record ChecklistItemResponse(
        Long checklistId,
        Long ritualId,
        String ritualName,
        Long itemId,
        String itemName,
        Integer quantity,
        String checkNote
) {}
