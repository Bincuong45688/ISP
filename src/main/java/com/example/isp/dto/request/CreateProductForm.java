package com.example.isp.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateProductForm {

    @NotBlank
    private String productName;

    @NotNull @DecimalMin("0.0")
    private BigDecimal price;

    private String productDescription;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long regionId;

    @Schema(type = "string", format = "binary", description = "Ảnh sản phẩm")
    @NotNull
    private MultipartFile file; // <-- Swagger sẽ hiện Choose file
}
