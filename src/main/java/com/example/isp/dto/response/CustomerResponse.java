package com.example.isp.dto.response;


import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CustomerResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String customerName;
    private String gender;
    private String address;
}
