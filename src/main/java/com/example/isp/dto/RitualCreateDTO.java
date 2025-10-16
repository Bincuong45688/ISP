package com.example.isp.dto;

import lombok.Data;

@Data
public class RitualCreateDTO {
    private String ritualName;
    private String description;
    private String region;
    private boolean active;
}
