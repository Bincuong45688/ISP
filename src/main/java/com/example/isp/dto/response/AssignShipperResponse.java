package com.example.isp.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignShipperResponse {
    private Long orderId;
    private String orderCode;
    private String shipperName;
}

