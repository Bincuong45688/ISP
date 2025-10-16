
package com.example.isp.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter @Setter
public class ProductResponse {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private String productDescription;
    private String productImage;

    private Long categoryId;
    private String categoryName;

    private Long regionId;
    private String regionName;


}
