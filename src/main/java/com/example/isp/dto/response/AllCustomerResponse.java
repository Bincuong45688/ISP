package com.example.isp.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AllCustomerResponse {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String customerName;
    private String gender;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
