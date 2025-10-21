package com.example.isp.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class RegisterCustomerRequest extends RegisterRequest {
    // Customer info
    private String customerName;

    private LocalDate birthDate;

    private String gender;

    private String address;
}
