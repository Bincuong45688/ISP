package com.example.isp.dto.response;

import com.example.isp.model.enums.Unit;

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
