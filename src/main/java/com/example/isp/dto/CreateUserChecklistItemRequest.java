package com.example.isp.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateUserChecklistItemRequest {
    private Long userChecklistId;
    private Long itemId;
    private Integer quantity;
    private String note;
}
