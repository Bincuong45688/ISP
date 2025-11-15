package com.example.isp.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StaffResponse {
    private Long staffId;
    private String staffName;
    private String email;
    private String phone;
    private String gender;
}
