package com.example.isp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChecklistDTO {
    private Long checklistId;
    private Long ritualId;
    private Long itemId;
    private int quantity;
    private String checkNote;
}
