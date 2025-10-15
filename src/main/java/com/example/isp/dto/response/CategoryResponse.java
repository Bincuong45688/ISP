package com.example.isp.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {

    private Long categoryId;        // ID danh mục
    private String categoryName;    // Tên danh mục
    private String description;     // Mô tả danh mục
}
