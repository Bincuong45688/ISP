package com.example.isp.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class ManagerResponse {
    private Long id;
    private String managerName;
    private String username;
    private String email;
    private String phone;
    private String role;
}
