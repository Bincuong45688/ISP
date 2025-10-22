package com.example.isp.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateChecklistItemRequest(
        @NotBlank(message = "Tên vật phẩm không được để trống")
        @Size(max = 255, message = "Tên vật phẩm không được vượt quá 255 ký tự")
        String itemName,

        @Size(max = 255, message = "Đơn vị tính không được vượt quá 255 ký tự")
        String unit
) {}
