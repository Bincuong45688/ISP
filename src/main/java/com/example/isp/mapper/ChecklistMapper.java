package com.example.isp.mapper;

import com.example.isp.dto.response.ChecklistResponse;
import com.example.isp.model.Checklist;

public final class ChecklistMapper {

    private ChecklistMapper() {}

    public static ChecklistResponse toResponse(Checklist c) {
        if (c == null) return null;

        return new ChecklistResponse(
                c.getChecklistId(),
                c.getRitual() != null ? c.getRitual().getRitualId() : null,
                c.getRitual() != null ? c.getRitual().getRitualName() : null,
                c.getItem() != null ? c.getItem().getItemId() : null,
                c.getItem() != null ? c.getItem().getItemName() : null,
                c.getItem() != null ? c.getItem().getUnit() : null,
                c.getQuantity(),
                c.getCheckNote()
        );
    }
}
