package com.example.isp.dto.request;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateUserChecklistItemRequest {
    private Integer quantity;
    private Boolean checked;
    private String note;
}
