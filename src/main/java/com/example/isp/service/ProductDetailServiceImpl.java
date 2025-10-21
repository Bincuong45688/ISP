package com.example.isp.service;

import com.example.isp.model.ProductDetail;
import com.example.isp.repository.ProductDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductDetailServiceImpl implements ProductDetailService {

    private final ProductDetailRepository productDetailRepository;

    @Override
    public ProductDetail create(ProductDetail d) {
        return productDetailRepository.save(d);
    }

    @Override
    public ProductDetail update(Long id, ProductDetail u) {
        ProductDetail existing = productDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found: " + id));

        existing.setProduct(u.getProduct());
        existing.setItemId(u.getItemId());
        existing.setProDetailQuantity(u.getProDetailQuantity());

        return productDetailRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductDetail> byProduct(Long productId) {
        return productDetailRepository.findByProduct_ProductId(productId);
    }


    @Override
    public void delete(Long id) {
        if (!productDetailRepository.existsById(id)) {
            throw new EntityNotFoundException("ProductDetail not found: " + id);
        }
        productDetailRepository.deleteById(id);
    }
}
