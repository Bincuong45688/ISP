package com.example.isp.service;

import com.example.isp.model.Product;
import com.example.isp.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product create(Product p) {
        return productRepository.save(p);
    }

    @Override
    public Product update(Long id, Product u) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy sản phẩm: " + id));

        existing.setProductName(u.getProductName());
        existing.setPrice(u.getPrice());
        existing.setProductDescription(u.getProductDescription());
        existing.setProductImage(u.getProductImage());
        existing.setCategory(u.getCategory());
        existing.setRegion(u.getRegion());

        return productRepository.save(existing); // đảm bảo ghi lại vào DB
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> list() {
        return productRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> byCategory(Long categoryId) {
        return productRepository.findByCategory_CategoryId(categoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> byRegion(Long regionId) {
        return productRepository.findByRegion_RegionId(regionId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> search(String q) {
        return productRepository.findByProductNameContainingIgnoreCase(q);
    }

    @Override
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Không tìm thấy sản phẩm: " + id);
        }
        productRepository.deleteById(id);
    }
}
