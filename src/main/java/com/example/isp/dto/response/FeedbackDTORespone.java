package com.example.isp.dto.response;


import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDTORespone {
    private Long fbId;
    private Long orderId;
    private String userName;
    private String content;
    private int star;
    private LocalDateTime createdAt;
    private String imageUrls;
    private String videoUrls;
}

