package com.example.isp.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
public class CreateProductRequest {

    @NotBlank(message = "Tên sản phẩm không được để trống")
    private String productName;

    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá sản phẩm phải >= 0")
    private BigDecimal price;

    private String productDescription;   // mô tả sản phẩm
    private String productImage;         // link ảnh sản phẩm

    @NotNull(message = "Danh mục không được để trống")
    private Long categoryId;             // FK -> Category(category_id)

    @NotNull(message = "Khu vực không được để trống")
    private Long regionId;               // FK -> Region(region_id)
}
