package com.example.isp.service;

import com.example.isp.model.Product;
import com.example.isp.repository.ProductRepository;
import com.example.isp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Product> list() {
        return productRepo.findAllWithRelations(Sort.by(Sort.Order.desc("productId")));
    }

    @Override
    @Transactional(readOnly = true)
    public Product get(Long id) {
        return productRepo.findByIdWithRelations(id).orElseThrow();
    }

    @Override
    public Product create(Product p) {
        return productRepo.save(p);
    }

    @Override
    public Product update(Long id, Product patch) {
        Product cur = productRepo.findById(id).orElseThrow();
        if (patch.getProductName() != null) cur.setProductName(patch.getProductName());
        if (patch.getPrice() != null) cur.setPrice(patch.getPrice());
        if (patch.getProductDescription() != null) cur.setProductDescription(patch.getProductDescription());
        if (patch.getProductImage() != null) cur.setProductImage(patch.getProductImage());
        if (patch.getCategory() != null) cur.setCategory(patch.getCategory());
        if (patch.getRegion() != null) cur.setRegion(patch.getRegion());
        if (patch.getStatus() != null) cur.setStatus(patch.getStatus());
        return productRepo.save(cur);
    }

    @Override
    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> searchByName(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return productRepo.findAllWithRelations(Sort.by(Sort.Order.desc("productId")));
        }
        return productRepo.searchByName(keyword);
    }
}
