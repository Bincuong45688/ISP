package com.example.isp.dto.request;

import com.example.isp.model.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateChecklistItemRequest(
        @NotBlank(message = "Tên vật phẩm không được để trống")
        @Size(max = 255, message = "Tên vật phẩm không được vượt quá 255 ký tự")
        String itemName,

        @NotNull(message = "Đơn vị tính không được để trống")
        Unit unit,

        Integer stockQuantity
) {}
