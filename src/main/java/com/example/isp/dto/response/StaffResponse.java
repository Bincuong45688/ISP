package com.example.isp.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class StaffResponse {
    private Long id;
    private String staffName;
    private String username;
    private String email;
    private String phone;
    private String role;
}
