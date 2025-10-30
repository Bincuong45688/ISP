package com.example.isp.dto.request;

import lombok.Data;

@Data
public class UpdateShipperProfileRequest {
    private String shipperName;
    private String email;
    private String phone;
    private String gender;
}
