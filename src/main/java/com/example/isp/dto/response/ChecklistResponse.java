package com.example.isp.dto.response;

import com.example.isp.model.Unit;

public record ChecklistResponse(
        Long checklistId,
        Long ritualId,
        String ritualName,
        Long itemId,
        String itemName,
        Unit unit,
        Integer quantity,
        String checkNote
) {}
