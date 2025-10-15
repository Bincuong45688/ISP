package com.example.isp.repository;

import com.example.isp.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Lấy tất cả sản phẩm theo Category ID
    List<Product> findByCategory_CategoryId(Long categoryId);

    // Lấy tất cả sản phẩm theo Region ID
    List<Product> findByRegion_RegionId(Long regionId);

    // Tìm sản phẩm theo tên (không phân biệt hoa/thường)
    List<Product> findByProductNameContainingIgnoreCase(String q);
}
