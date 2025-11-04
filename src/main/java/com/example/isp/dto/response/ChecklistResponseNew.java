package com.example.isp.dto.response;

import com.example.isp.model.enums.Unit;

public record ChecklistResponseNew(
    Long checklistId,
    Long itemId,
    String itemName,
    Unit unit,
    Integer quantity,
    String checkNote
) {}


