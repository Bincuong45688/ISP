package com.example.isp.dto.request;

import jakarta.validation.constraints.Size;

public record UpdateChecklistItemRequest(
        @Size(max = 255, message = "Tên vật phẩm không được vượt quá 255 ký tự")
        String itemName,

        @Size(max = 255, message = "Đơn vị tính không được vượt quá 255 ký tự")
        String unit
) {}
