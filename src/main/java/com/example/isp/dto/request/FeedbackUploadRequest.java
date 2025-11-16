package com.example.isp.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackUploadRequest {

    private Long orderId;      // Id đơn hàng
    private Long userId;       // Id người dùng
    private Long productId;    // Id sản phẩm

    private String content;    // Nội dung đánh giá
    private int star;          // Số sao (1–5)

    // Upload nhiều ảnh (có thể null)
    private MultipartFile[] images;

}
