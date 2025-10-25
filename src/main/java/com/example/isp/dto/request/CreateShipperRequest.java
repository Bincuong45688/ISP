package com.example.isp.dto.request;


import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CreateShipperRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String shipperName;
    private String gender;
}
