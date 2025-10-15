package com.example.isp.controller;

import com.example.isp.dto.request.CreateProductRequest;
import com.example.isp.dto.response.ProductResponse;
import com.example.isp.dto.request.UpdateProductRequest;
import com.example.isp.model.Category;
import com.example.isp.model.Product;
import com.example.isp.model.Region;
import com.example.isp.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody CreateProductRequest req) {
        Product p = new Product();
        p.setProductName(req.getProductName());
        p.setPrice(req.getPrice());
        p.setProductDescription(req.getProductDescription());
        p.setProductImage(req.getProductImage());
        p.setCategory(Category.builder().categoryId(req.getCategoryId()).build());
        p.setRegion(Region.builder().regionId(req.getRegionId()).build());

        Product saved = productService.create(p);
        return toProductResponse(saved);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody UpdateProductRequest req) {
        Product u = new Product();
        u.setProductName(req.getProductName());
        u.setPrice(req.getPrice());
        u.setProductDescription(req.getProductDescription());
        u.setProductImage(req.getProductImage());
        if (req.getCategoryId() != null) {
            u.setCategory(Category.builder().categoryId(req.getCategoryId()).build());
        }
        if (req.getRegionId() != null) {
            u.setRegion(Region.builder().regionId(req.getRegionId()).build());
        }

        Product saved = productService.update(id, u);
        return toProductResponse(saved);
    }

    @GetMapping
    public List<ProductResponse> list() {
        return productService.list().stream().map(this::toProductResponse).toList();
    }

    @GetMapping("/by-category/{categoryId}")
    public List<ProductResponse> byCategory(@PathVariable Long categoryId) {
        return productService.byCategory(categoryId).stream().map(this::toProductResponse).toList();
    }

    @GetMapping("/by-region/{regionId}")
    public List<ProductResponse> byRegion(@PathVariable Long regionId) {
        return productService.byRegion(regionId).stream().map(this::toProductResponse).toList();
    }

    // (tuỳ chọn) Tìm kiếm theo tên sản phẩm
    @GetMapping("/search")
    public List<ProductResponse> search(@RequestParam("q") String q) {
        return productService.search(q).stream().map(this::toProductResponse).toList();
    }

    // (tuỳ chọn) Xoá sản phẩm
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    private ProductResponse toProductResponse(Product s) {
        return ProductResponse.builder()
                .productId(s.getProductId())
                .productName(s.getProductName())
                .price(s.getPrice())
                .productDescription(s.getProductDescription())
                .productImage(s.getProductImage())
                .categoryId(s.getCategory() != null ? s.getCategory().getCategoryId() : null)
                .categoryName(s.getCategory() != null ? s.getCategory().getCategoryName() : null)
                .regionId(s.getRegion() != null ? s.getRegion().getRegionId() : null)
                .regionName(s.getRegion() != null ? s.getRegion().getRegionName() : null)
                .build();
    }
}
