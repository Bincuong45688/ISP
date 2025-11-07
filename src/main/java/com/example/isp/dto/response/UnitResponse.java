package com.example.isp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UnitResponse {

    private Long unitId;
    private String unitName;
    private String displayName;
    private String description;
}
