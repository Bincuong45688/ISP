package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ProductDetail")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "productDetail_id")
    private Long productDetailId;

    // FK -> Product(product_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // Tạm thời chưa có bảng CheclListItem -> giữ item_id là cột số
    @Column(name = "item_id", nullable = false)
    private Integer itemId;

    @Column(name = "proDetail_quantity", nullable = false)
    private Integer proDetailQuantity;
}
