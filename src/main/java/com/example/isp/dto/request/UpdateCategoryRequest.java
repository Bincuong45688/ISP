package com.example.isp.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {

    private String categoryName;   // Tên danh mục mới (nếu có thay đổi)
    private String description;    // Mô tả mới (có thể null nếu không chỉnh)
}
