package com.example.isp.service;

import com.example.isp.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    Product update(Long id, Product product);
    Product get(Long id);
    List<Product> list();
    void delete(Long id);
}
