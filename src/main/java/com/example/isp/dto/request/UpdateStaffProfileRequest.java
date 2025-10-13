package com.example.isp.dto.request;

import lombok.Data;

@Data
public class UpdateStaffProfileRequest {
    private String staffName;
    private String email;
    private String phone;
    private String password;
}
