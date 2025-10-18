package com.example.isp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RitualUpdateDTO {
    private String ritualName;
    private String description;
    private Long regionId;
    private String dateLunar;
    private LocalDate dateSolar;
    private String meaning;
    private boolean active;
    private String imageUrl;
}
