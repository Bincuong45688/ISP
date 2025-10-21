package com.example.isp.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateChecklistItemRequest(
        @NotNull(message = "Ritual ID không được để trống")
        Long ritualId,

        @NotNull(message = "Item ID không được để trống")
        Long itemId,

        @Min(value = 1, message = "Số lượng phải lớn hơn 0")
        Integer quantity,

        @Size(max = 255, message = "Ghi chú không được vượt quá 255 ký tự")
        String checkNote
) {}
