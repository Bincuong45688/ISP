package com.example.isp.dto.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String customerName;
    private String gender;
    private String address;
    private String phoneNumber;
}
