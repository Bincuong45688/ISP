package com.example.isp.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class RegisterStaffRequest extends RegisterRequest {
    private String staffName;
}
