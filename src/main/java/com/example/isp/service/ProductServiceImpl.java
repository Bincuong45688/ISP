package com.example.isp.service;

import com.example.isp.model.Product;
import com.example.isp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    // ===== Thêm mới: Bộ lọc động vùng–loại–giá (có phân trang & sort) =====
    @Override
    @Transactional(readOnly = true)
    public Page<Product> filter(Long regionId,
                                Long categoryId,
                                BigDecimal minPrice,
                                BigDecimal maxPrice,
                                Pageable pageable) {

        // Chuẩn hoá khoảng giá mà KHÔNG gán lại tham số
        BigDecimal min = minPrice;
        BigDecimal max = maxPrice;
        if (min != null && max != null && min.compareTo(max) > 0) {
            BigDecimal t = min; min = max; max = t;
        }

        // Tạo biến final để dùng trong lambda Specification
        final BigDecimal minP = min;
        final BigDecimal maxP = max;
        final Long regionIdF = regionId;
        final Long categoryIdF = categoryId;

        Specification<Product> spec = Specification.allOf();

        if (regionIdF != null) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(root.get("region").get("regionId"), regionIdF));
        }
        if (categoryIdF != null) {
            spec = spec.and((root, q, cb) ->
                    cb.equal(root.get("category").get("categoryId"), categoryIdF));
        }
        if (minP != null) {
            spec = spec.and((root, q, cb) ->
                    cb.greaterThanOrEqualTo(root.get("price"), minP));
        }
        if (maxP != null) {
            spec = spec.and((root, q, cb) ->
                    cb.lessThanOrEqualTo(root.get("price"), maxP));
        }

        return productRepo.findAll(spec, pageable);
    }

}
