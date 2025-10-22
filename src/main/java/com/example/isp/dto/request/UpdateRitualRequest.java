package com.example.isp.dto.request;

import java.time.LocalDate;

public record UpdateRitualRequest(
        String ritualName,
        String dateLunar,
        Long regionId,
        LocalDate dateSolar,
        String description,
        String meaning
) {}
