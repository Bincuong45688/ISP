package com.example.isp.dto.request;

import com.example.isp.model.enums.DiscountType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UpdateVoucherRequest {

    @Size(max = 500, message = "Description must not exceed 500 characters")
    private String description;

    private DiscountType discountType;

    @DecimalMin(value = "0.0", inclusive = false, message = "Discount value must be greater than 0")
    private BigDecimal discountValue;

    @DecimalMin(value = "0.0", message = "Minimum order amount must be non-negative")
    private BigDecimal minOrderAmount;

    @DecimalMin(value = "0.0", message = "Maximum discount amount must be non-negative")
    private BigDecimal maxDiscountAmount;

    @Min(value = 1, message = "Usage limit must be at least 1")
    private Integer usageLimit;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Boolean isActive;
}
