package com.example.isp.dto.request;


import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDTO {
    private Long orderId;
    private Long userID;      // Đánh giá cho đơn hàng nào
    // Đánh giá cho đơn hàng nào
    private String content;    // Nội dung feedback
    private int star;          // Số sao (1–5)
    private LocalDateTime createdAt; // Tùy chọn (có thể set trong backend)
}
