package com.example.isp.service;

import com.example.isp.dto.request.AssignChecklistRequest;
import com.example.isp.dto.request.ChecklistItemRequest;
import com.example.isp.dto.response.ProductDetailResponse;
import com.example.isp.mapper.ProductDetailMapper;
import com.example.isp.model.Checklist;
import com.example.isp.model.ChecklistItem;
import com.example.isp.model.ProductDetail;
import com.example.isp.model.enums.ChecklistStatus;
import com.example.isp.repository.ChecklistItemRepository;
import com.example.isp.repository.ChecklistRepository;
import com.example.isp.repository.ProductDetailRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    @Override
    @Transactional(readOnly = true)
    public List<ProductDetailResponse> getByProduct(Long productId) {
        return productDetailRepository.findByIdWithAll(productId)
                .stream().map(ProductDetailMapper::toResponse).toList();
    }


    @Override
    @Transactional
    public ProductDetail assignChecklists(Long productDetailId, AssignChecklistRequest req) {
        // Load đủ dữ liệu để tránh Lazy
        ProductDetail pd = productDetailRepository.findByIdWithAll(productDetailId)
                .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy ProductDetail: " + productDetailId));

        // Lấy collection managed (KHÔNG clear)
        List<Checklist> managed = pd.getChecklists();
        if (managed == null) {
            managed = new ArrayList<>();
            pd.setChecklists(managed);
        }

        // Tập itemId đã tồn tại để tránh thêm trùng
        Set<Long> existingItemIds = managed.stream()
                .map(c -> c.getItem().getItemId())
                .collect(java.util.stream.Collectors.toSet());

        for (ChecklistItemRequest it : req.getChecklists()) {
            // nếu đã có item này thì bỏ qua (giữ nguyên checklist cũ)
            if (existingItemIds.contains(it.getItemId())) {
                continue;
            }

            ChecklistItem item = checklistItemRepository.findById(it.getItemId())
                    .orElseThrow(() -> new EntityNotFoundException("Không tìm thấy item: " + it.getItemId()));

            Checklist cl = Checklist.builder()
                    .productDetail(pd)               // quan trọng: set owner
                    .item(item)
                    .quantity(it.getQuantity())
                    .status(ChecklistStatus.PENDING)
                    .build();

            managed.add(cl);                     // chỉ thêm mới, không xóa cái cũ
        }

        return productDetailRepository.save(pd);
    }




}
