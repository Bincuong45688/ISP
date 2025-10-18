package com.example.isp.dto.request;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VerifyEmailRequest {
    private String email;
    private String otp;
}
