package com.example.isp.dto.response;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StaffProfileResponse {
    private String username;
    private String staffName;
    private String gender;
    private String phone;
    private String email;
    private String status;
}
