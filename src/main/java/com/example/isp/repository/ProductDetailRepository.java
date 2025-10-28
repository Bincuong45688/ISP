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
        select pd
        from ProductDetail pd
        left join fetch pd.checklists cl
        left join fetch cl.item i
        left join fetch cl.ritual r
        where pd.productDetailId = :id
        """)
    Optional<ProductDetail> findByIdWithChecklists(@Param("id") Long id);




}
