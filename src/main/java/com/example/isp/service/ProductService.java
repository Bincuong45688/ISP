// src/main/java/com/example/isp/service/ProductService.java
package com.example.isp.service;

import com.example.isp.dto.request.CreateProductRequest;
import com.example.isp.dto.request.UpdateProductRequest;
import com.example.isp.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> list();
    ProductResponse get(Long id);
    ProductResponse create(CreateProductRequest req);
    ProductResponse update(Long id, UpdateProductRequest req);
    void delete(Long id);

    // optional filter/search
    List<ProductResponse> byCategory(Long categoryId);
    List<ProductResponse> byRegion(Long regionId);
    List<ProductResponse> search(String q);
}
