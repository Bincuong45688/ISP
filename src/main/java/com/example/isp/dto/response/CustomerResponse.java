package com.example.isp.dto.response;


import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class CustomerResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String customerName;
    private LocalDate birthDate;
    private String gender;
    private String address;
}
