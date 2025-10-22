package com.example.isp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CreateRitualRequest(
        @NotBlank(message = "Tên nghi lễ không được để trống") String ritualName,
        String dateLunar,
        @NotNull(message = "Region ID không được để trống") Long regionId,
        LocalDate dateSolar,
        String description,
        String meaning
) {}
