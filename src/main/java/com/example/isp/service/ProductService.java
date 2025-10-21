package com.example.isp.service;

import com.example.isp.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {

    // ===== Giữ nguyên code cũ =====
    List<Product> list();
    Product get(Long id);
    Product create(Product p);
    Product update(Long id, Product patch);
    void delete(Long id);

    // Search đơn giản theo tên
    List<Product> searchByName(String keyword);

    // ===== Thêm mới: Bộ lọc động =====
    Page<Product> filter(Long regionId,
                         Long categoryId,
                         BigDecimal minPrice,
                         BigDecimal maxPrice,
                         Pageable pageable);
}
