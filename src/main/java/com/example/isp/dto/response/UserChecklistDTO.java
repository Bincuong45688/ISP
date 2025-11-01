package com.example.isp.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserChecklistDTO {
    private Long userChecklistId;
    private Long userId;
    private String userName;
    private Long ritualId;
    private String ritualName;
    private String title;
    private LocalDateTime createdAt;
    private LocalDateTime reminderDate;
    private Boolean isNotified;
    private List<UserChecklistItemDTO> items;
}
