package com.example.isp.dto.request;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateUserChecklistRequest {
    private Long userId;
    private Long ritualId;
    private String title;
    private LocalDateTime reminderDate;
}
