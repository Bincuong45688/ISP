// dto/request/UpdateProductRequest.java
package com.example.isp.dto.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter @Setter
public class UpdateProductRequest {
    @NotBlank
    private String productName;

    @NotNull @DecimalMin("0.0")
    private BigDecimal price;

    private String productDescription;
    private String productImage;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long regionId;

}
