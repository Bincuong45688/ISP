package com.example.isp.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class ShipperProfileResponse {
    private String username;
    private String shipperName;
    private String gender;
    private String phone;
    private String email;
    private String status;
}