package com.example.isp.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateUserChecklistRequest {
    private Long userId;
    private Long ritualId;
    private String title;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @CreationTimestamp
    private LocalDateTime reminderDate;
}
