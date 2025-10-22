// com.example.isp.dto.request.AdjustCartItemRequest
package com.example.isp.dto.request;

import lombok.Data;

@Data
public class AdjustCartItemRequest {
    private Long productId;
    private Integer quantity; // mặc định 1 nếu null/<=0
}
