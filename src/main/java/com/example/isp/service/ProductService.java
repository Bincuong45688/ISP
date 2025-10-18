package com.example.isp.service;

import com.example.isp.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> list();
    Product get(Long id);
    Product create(Product p);
    Product update(Long id, Product patch);
    void delete(Long id);

    // Search đơn giản theo tên
    List<Product> searchByName(String keyword);
}
