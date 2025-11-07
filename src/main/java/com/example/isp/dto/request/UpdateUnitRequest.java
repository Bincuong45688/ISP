package com.example.isp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUnitRequest {

    private String unitName;
    private String displayName;
    private String description;
}
