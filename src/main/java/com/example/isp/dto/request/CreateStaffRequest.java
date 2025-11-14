package com.example.isp.dto.request;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CreateStaffRequest{
    private String username;
    private String password;
    private String email;
    private String phone;
    private String staffName;
    private String gender;
}
