package com.example.isp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {

    private Long productId;       // ID sản phẩm
    private String productName;   // Tên sản phẩm
    private Integer quantity;     // Số lượng trong giỏ
    private Boolean selected;     // Trạng thái được chọn (true/false)
}
