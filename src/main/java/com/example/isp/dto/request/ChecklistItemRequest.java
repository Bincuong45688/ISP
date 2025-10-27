package com.example.isp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChecklistItemRequest {
    private Long itemId;
    private Integer quantity;
}
