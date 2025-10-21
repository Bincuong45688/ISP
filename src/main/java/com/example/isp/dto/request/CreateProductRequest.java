package com.example.isp.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateProductRequest {

    @NotBlank
    private String productName;

    @NotNull @DecimalMin("0.0")
    private BigDecimal price;

    private String productDescription;

    private String productImage; // có thể là URL hoặc upload sau

    @NotNull
    private Long categoryId;

    @NotNull
    private Long regionId;
}
