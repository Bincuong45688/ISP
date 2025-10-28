package com.example.isp.service;

import com.example.isp.dto.request.AssignChecklistRequest;
import com.example.isp.dto.response.ProductDetailResponse;
import com.example.isp.mapper.ProductDetailMapper;
import com.example.isp.model.Checklist;
import com.example.isp.model.ProductDetail;
import com.example.isp.repository.ChecklistItemRepository;
import com.example.isp.repository.ChecklistRepository;
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
    private final ChecklistItemRepository checklistItemRepository;
    private final ChecklistRepository checklistRepository;

    @Override
    public ProductDetail create(ProductDetail d) {
        return productDetailRepository.save(d);
    }

    @Override
    public ProductDetail update(Long id, ProductDetail u) {
        ProductDetail existing = productDetailRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductDetail not found: " + id));

        existing.setProduct(u.getProduct());
        existing.setChecklists(u.getChecklists());


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

    //
    @Transactional(readOnly = true)
    @Override
    public ProductDetailResponse getDetailById(Long productDetailId) {
        ProductDetail pd = productDetailRepository.findByIdWithChecklists(productDetailId)
                .orElseThrow(() -> new RuntimeException("ProductDetail not found: " + productDetailId));
        return ProductDetailMapper.toResponse(pd);
    }


    @Override
    @Transactional
    public void assignChecklists(Long productDetailId, AssignChecklistRequest req) {
        ProductDetail detail = productDetailRepository.findById(productDetailId)
                .orElseThrow(() -> new RuntimeException("ProductDetail not found"));

        for (Long checklistId : req.getChecklistIds()) {
            Checklist checklist = checklistRepository.findById(checklistId)
                    .orElseThrow(() -> new RuntimeException("Checklist not found with ID: " + checklistId));

            checklist.setProductDetail(detail); // liên kết lại với productDetail
            checklistRepository.save(checklist);
        }
        productDetailRepository.save(detail);
    }





}
