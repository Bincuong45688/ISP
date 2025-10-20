package com.example.isp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemResponse {

    private Long productId;
    private String productName;
    private Integer quantity;     
    private Boolean selected;     // Trạng thái được chọn (true/false)
}
