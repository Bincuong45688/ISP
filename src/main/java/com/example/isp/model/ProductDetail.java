package com.example.isp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_detail")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_detail_id")
    private Long productDetailId;

    // FK -> Product(product_id)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // FK -> CheckList(checklist_id)
    // ProductDetail.java
    @OneToMany(mappedBy = "productDetail",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @Builder.Default
    private List<Checklist> checklists = new ArrayList<>();

}
