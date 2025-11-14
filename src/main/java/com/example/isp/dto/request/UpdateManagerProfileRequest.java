package com.example.isp.dto.request;

import lombok.Data;

@Data
public class UpdateManagerProfileRequest {
    private String managerName;
    private String email;
    private String phone;
    private String password;
}
