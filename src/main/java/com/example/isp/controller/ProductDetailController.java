package com.example.isp.controller;

import com.example.isp.dto.request.AssignChecklistRequest;
import com.example.isp.dto.response.ProductDetailResponse;
import com.example.isp.mapper.ProductDetailMapper;
import com.example.isp.model.ProductDetail;
import com.example.isp.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-details")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    /**
     * Lấy danh sách ProductDetail theo productId
     */
//    @GetMapping("/by-product/{productId}")
//    public List<ProductDetail> byProduct(@PathVariable Long productId) {
//        return productDetailService.byProduct(productId);
//    }

    /**
     * Tạo mới một ProductDetail
     */
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public ProductDetail create(@RequestBody ProductDetail productDetail) {
//        return productDetailService.create(productDetail);
//    }

    /**
     * Cập nhật ProductDetail theo id
     */
    @PutMapping("/{id}")
    public ProductDetail update(@PathVariable Long id, @RequestBody ProductDetail productDetail) {
        return productDetailService.update(id, productDetail);
    }

    /**
     * Xóa ProductDetail theo id
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productDetailService.delete(id);
    }

    // Tao làm
    @GetMapping("/{productDetailId}/details")
    public ResponseEntity<ProductDetailResponse> getDetailById(
            @PathVariable Long productDetailId) {
        return ResponseEntity.ok(productDetailService.getDetailById(productDetailId));
    }

    @PostMapping("/{productDetailId}/assign-checklists")
    public ResponseEntity<?> assignChecklists(
            @PathVariable Long productDetailId,
            @RequestBody AssignChecklistRequest request) {
        productDetailService.assignChecklists(productDetailId, request);
        return ResponseEntity.ok("Assigned checklists successfully");
    }




}
