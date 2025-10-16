package com.example.isp.repository;

import com.example.isp.model.Product;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // ----- Mặc định: luôn fetch category + region để tránh LazyInitializationException -----

    @Override
    @EntityGraph(attributePaths = {"category", "region"})
    List<Product> findAll();

    @Override
    @EntityGraph(attributePaths = {"category", "region"})
    Optional<Product> findById(Long id);

    // ----- Lấy tất cả sản phẩm theo Category ID -----
    @EntityGraph(attributePaths = {"category", "region"})
    List<Product> findByCategory_CategoryId(Long categoryId);

    // ----- Lấy tất cả sản phẩm theo Region ID -----
    @EntityGraph(attributePaths = {"category", "region"})
    List<Product> findByRegion_RegionId(Long regionId);

    // ----- Tìm sản phẩm theo tên (không phân biệt hoa/thường) -----
    @EntityGraph(attributePaths = {"category", "region"})
    List<Product> findByProductNameContainingIgnoreCase(String q);
}
