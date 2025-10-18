package com.example.isp.dto;

import lombok.Data;

@Data
public class RitualUpdateDTO {
    private String ritualName;
    private String description;
    private String region;
    private boolean active;
}
