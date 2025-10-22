package com.example.isp.dto.response;

public record ChecklistResponse(
        Long checklistId,
        Long ritualId,
        String ritualName,
        Long itemId,
        String itemName,
        Integer quantity,
        String checkNote
) {}
