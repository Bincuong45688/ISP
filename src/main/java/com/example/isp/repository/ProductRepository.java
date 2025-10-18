package com.example.isp.repository;

import com.example.isp.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // List tất cả + kèm category & region (tránh LazyInitializationException khi map)
    @EntityGraph(attributePaths = {"category", "region"})
    @Query("select p from Product p")
    List<Product> findAllWithRelations(Sort sort);

    // Lấy 1 sản phẩm + kèm category & region
    @EntityGraph(attributePaths = {"category", "region"})
    @Query("select p from Product p where p.productId = :id")
    Optional<Product> findByIdWithRelations(@Param("id") Long id);

    // Search theo tên + kèm category & region
    @EntityGraph(attributePaths = {"category", "region"})
    @Query("""
        select p from Product p
        where lower(p.productName) like lower(concat('%', :keyword, '%'))
        """)
    List<Product> searchByName(@Param("keyword") String keyword);
}
