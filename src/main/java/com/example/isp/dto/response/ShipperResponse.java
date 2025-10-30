package com.example.isp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShipperResponse {
    private Long shipperId;
    private String shipperName;
    private String phone;
    private String email;
    private String gender;
}
