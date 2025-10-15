package com.example.isp.dto.request;

import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class UpdateProductRequest {

    private String productName;        // Tên sản phẩm mới (nếu đổi)
    private BigDecimal price;          // Giá mới (nếu đổi)
    private String productDescription; // Mô tả mới
    private String productImage;       // Ảnh mới
    private Long categoryId;           // Chuyển sang danh mục khác (nếu có)
    private Long regionId;             // Chuyển sang vùng khác (nếu có)
}
