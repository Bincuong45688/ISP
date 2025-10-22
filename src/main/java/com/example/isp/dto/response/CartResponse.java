package com.example.isp.dto.response;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartResponse {

    private Long cartId;                          // ID của giỏ hàng
    private String cartStatus;                    // Trạng thái (ACTIVE, CHECKED_OUT, v.v.)
    private Long customerId;                      // Mã khách hàng sở hữu giỏ hàng
    private String customerName;                  // Tên khách hàng
    private List<CartItemResponse> items;         // Danh sách các sản phẩm trong giỏ
}

