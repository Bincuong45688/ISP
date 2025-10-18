package com.example.isp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateProductForm {
    private String productName;
    private BigDecimal price;
    private String productDescription;
    private Long categoryId;
    private Long regionId;

    @Schema(type = "string", format = "binary", description = "Ảnh mới (tùy chọn)")
    private MultipartFile file; // có thể null
}
