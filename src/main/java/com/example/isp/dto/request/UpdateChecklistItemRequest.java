package com.example.isp.dto.request;

import com.example.isp.model.enums.Unit;
import jakarta.validation.constraints.Size;

public record UpdateChecklistItemRequest(
        @Size(max = 255, message = "Tên vật phẩm không được vượt quá 255 ký tự")
        String itemName,

        Unit unit,

        Integer stockQuantity
) {}
