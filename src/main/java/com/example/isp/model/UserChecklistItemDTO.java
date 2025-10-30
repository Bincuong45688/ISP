package com.example.isp.model;

import com.example.isp.model.enums.Unit;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserChecklistItemDTO {
    private Long userChecklistItemId;
    private Long itemId;
    private String itemName;
    private Unit unit;
    private Integer quantity;
    private Boolean checked;
    private String note;
    private Integer stockQuantity; // Số lượng tồn kho
}
