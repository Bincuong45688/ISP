package com.example.isp.repository;

import com.example.isp.model.Product;
import com.example.isp.model.enums.ProductStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {


    // Cho phép dùng Specification (lọc theo vùng miền, loại, giá...)
    @Override
    @EntityGraph(attributePaths = {"category", "region"})
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

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
    List<Product> findByStatus(ProductStatus status);
}
