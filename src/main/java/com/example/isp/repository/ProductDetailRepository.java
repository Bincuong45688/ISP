package com.example.isp.repository;

import com.example.isp.model.ProductDetail;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

    @EntityGraph(attributePaths = {
            "product",
            "product.category",
            "product.region"
    })
    List<ProductDetail> findByProduct_ProductId(Long productId);

    @Query("""
    SELECT DISTINCT pd
    FROM ProductDetail pd
    JOIN FETCH pd.product p
    LEFT JOIN FETCH p.category
    LEFT JOIN FETCH p.region
    LEFT JOIN FETCH pd.checklists c
    LEFT JOIN FETCH c.item
    WHERE pd.productDetailId = :id
""")
    Optional<ProductDetail> findByIdWithAll(@Param("id") Long id);




}
