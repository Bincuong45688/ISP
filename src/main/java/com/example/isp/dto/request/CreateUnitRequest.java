package com.example.isp.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUnitRequest {

    @NotBlank(message = "Tên đơn vị không được để trống")
    private String unitName;

    @NotBlank(message = "Tên hiển thị không được để trống")
    private String displayName;

    private String description;
}
