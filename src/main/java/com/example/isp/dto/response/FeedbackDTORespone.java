package com.example.isp.dto.response;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDTORespone {

    private Long fbId;

    private Long orderId;
    private Long userId;       // ✨ Rất quan trọng
    private Long productId;    // ✨ Rất quan trọng

    private String userName;   // có thể thay bằng tên thật của Customer

    private String content;
    private int star;
    private LocalDateTime createdAt;

    private String imageUrls;

}
