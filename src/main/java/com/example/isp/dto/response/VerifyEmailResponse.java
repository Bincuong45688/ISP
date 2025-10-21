package com.example.isp.dto.response;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class VerifyEmailResponse {
    private String message;
    private String status;
}
