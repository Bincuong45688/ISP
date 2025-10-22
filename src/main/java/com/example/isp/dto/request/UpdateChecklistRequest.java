package com.example.isp.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateChecklistRequest(
        @Size(max = 255, message = "Tên vật phẩm không được vượt quá 255 ký tự")
        String itemName,

        @Size(max = 5000, message = "Mô tả không được vượt quá 5000 ký tự")
        String itemDescription,

        @Size(max = 255, message = "Đơn vị tính không được vượt quá 255 ký tự")
        String unit
) {}
