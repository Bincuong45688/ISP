package com.example.isp.service;

import com.example.isp.model.*;
import com.example.isp.repository.*;
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

    @Override
    public Product create(Product product) {
        if (product.getCategory() != null) {
            Category category = categoryRepo.findById(product.getCategory().getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            product.setCategory(category);
        }
        if (product.getRegion() != null) {
            Region region = regionRepo.findById(product.getRegion().getRegionId())
                    .orElseThrow(() -> new EntityNotFoundException("Region not found"));
            product.setRegion(region);
        }
        return productRepo.save(product);
    }

    @Override
    public Product update(Long id, Product product) {
        Product existing = productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (product.getProductName() != null)
            existing.setProductName(product.getProductName());
        if (product.getPrice() != null)
            existing.setPrice(product.getPrice());
        if (product.getProductDescription() != null)
            existing.setProductDescription(product.getProductDescription());
        if (product.getProductImage() != null)
            existing.setProductImage(product.getProductImage());
        if (product.getCategory() != null)
            existing.setCategory(categoryRepo.findById(product.getCategory().getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found")));
        if (product.getRegion() != null)
            existing.setRegion(regionRepo.findById(product.getRegion().getRegionId())
                    .orElseThrow(() -> new EntityNotFoundException("Region not found")));

        return productRepo.save(existing);
    }

    @Override
    public Product get(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
    }

    @Override
    public List<Product> list() {
        return productRepo.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!productRepo.existsById(id))
            throw new EntityNotFoundException("Product not found");
        productRepo.deleteById(id);
    }
}
