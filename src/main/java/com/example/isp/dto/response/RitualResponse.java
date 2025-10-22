package com.example.isp.dto.response;

import java.time.LocalDate;

public record RitualResponse(
        Long ritualId,
        String ritualName,
        String dateLunar,
        Long regionId,
        String regionName,
        LocalDate dateSolar,
        String description,
        String meaning,
        String imageUrl
) {}
