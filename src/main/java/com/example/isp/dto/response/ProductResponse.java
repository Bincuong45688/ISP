package com.example.isp.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {

    private Long productId;           // ID sản phẩm
    private String productName;       // Tên sản phẩm
    private BigDecimal price;         // Giá
    private String productDescription;// Mô tả sản phẩm
    private String productImage;      // Hình ảnh

    private Long categoryId;          // FK -> Category
    private String categoryName;      // Tên danh mục

    private Long regionId;            // FK -> Region
    private String regionName;        // Tên vùng miền
}
