package com.example.isp.dto.response;

import java.time.LocalDate;
import java.util.List;

/**
 * Response DTO cho Ritual kèm theo danh sách Checklists.
 * Dùng khi cần xem chi tiết đầy đủ của một nghi lễ.
 */
public record   RitualDetailResponse(
        Long ritualId,
        String ritualName,
        String dateLunar,
        Long regionId,
        String regionName,
        LocalDate dateSolar,
        String description,
        String meaning,
        String imageUrl,
        List<ChecklistResponse> checklists
) {}
