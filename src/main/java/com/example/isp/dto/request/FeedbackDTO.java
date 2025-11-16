package com.example.isp.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackDTO {

    private Long orderId;      // Đơn hàng nào chứa sản phẩm
    private Long userId;       // Ai đánh giá
    private Long productId;    // Sản phẩm nào được đánh giá (BẮT BUỘC)

    private String content;    // Nội dung đánh giá
    private int star;          // Số sao 1–5

    private String imageUrls;  // Danh sách ảnh (phân cách dấu phẩy)

}
