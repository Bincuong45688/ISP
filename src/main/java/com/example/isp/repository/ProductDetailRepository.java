package com.example.isp.repository;

import com.example.isp.model.ProductDetail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    @EntityGraph(attributePaths = {
            "product",
            "product.category",
            "product.region"
    })
    List<ProductDetail> findByProduct_ProductId(Long productId);
}
