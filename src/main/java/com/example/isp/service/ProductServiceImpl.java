// src/main/java/com/example/isp/service/impl/ProductServiceImpl.java
package com.example.isp.service;

import com.example.isp.dto.request.CreateProductRequest;
import com.example.isp.dto.request.UpdateProductRequest;
import com.example.isp.dto.response.ProductResponse;
import com.example.isp.mapper.ProductMapper;
import com.example.isp.model.Product;
import com.example.isp.repository.CategoryRepository;
import com.example.isp.repository.ProductRepository;
import com.example.isp.repository.RegionRepository;
import com.example.isp.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;
    private final CategoryRepository categoryRepo;
    private final RegionRepository regionRepo;

    @Transactional(readOnly = true)
    public List<ProductResponse> list() {
        return productRepo.findAll().stream().map(ProductMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ProductResponse get(Long id) {
        var p = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
        return ProductMapper.toResponse(p);
    }

    public ProductResponse create(CreateProductRequest req) {
        var cat = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + req.getCategoryId()));
        var reg = regionRepo.findById(req.getRegionId())
                .orElseThrow(() -> new EntityNotFoundException("Region not found: " + req.getRegionId()));

        var p = new Product();
        p.setProductName(req.getProductName());
        p.setPrice(req.getPrice());
        p.setProductDescription(req.getProductDescription());
        p.setProductImage(req.getProductImage());
        p.setCategory(cat);
        p.setRegion(reg);

        return ProductMapper.toResponse(productRepo.save(p));
    }

    public ProductResponse update(Long id, UpdateProductRequest req) {
        var p = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));

        var cat = categoryRepo.findById(req.getCategoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + req.getCategoryId()));
        var reg = regionRepo.findById(req.getRegionId())
                .orElseThrow(() -> new EntityNotFoundException("Region not found: " + req.getRegionId()));

        p.setProductName(req.getProductName());
        p.setPrice(req.getPrice());
        p.setProductDescription(req.getProductDescription());
        p.setProductImage(req.getProductImage());
        p.setCategory(cat);
        p.setRegion(reg);

        return ProductMapper.toResponse(productRepo.save(p));
    }

    public void delete(Long id) {
        if (!productRepo.existsById(id)) {
            throw new EntityNotFoundException("Product not found: " + id);
        }
        productRepo.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> byCategory(Long categoryId) {
        return productRepo.findByCategory_CategoryId(categoryId).stream().map(ProductMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> byRegion(Long regionId) {
        return productRepo.findByRegion_RegionId(regionId).stream().map(ProductMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> search(String q) {
        return productRepo.findByProductNameContainingIgnoreCase(q).stream().map(ProductMapper::toResponse).toList();
    }
}
