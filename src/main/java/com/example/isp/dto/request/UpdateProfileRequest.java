package com.example.isp.dto.request;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    private String fullName;
    private String gender;
    private String address;
    private String phoneNumber;
}
