package com.example.isp.controller;

import com.example.isp.dto.request.*;
import com.example.isp.dto.response.ProductResponse;
import com.example.isp.mapper.ProductMapper;
import com.example.isp.model.*;
import com.example.isp.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponse> list() {
        return productService.list().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable Long id) {
        return ProductMapper.toResponse(productService.get(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody CreateProductRequest req) {
        Product p = Product.builder()
                .productName(req.getProductName())
                .price(req.getPrice())
                .productDescription(req.getProductDescription())
                .productImage(req.getProductImage())
                .category(Category.builder().categoryId(req.getCategoryId()).build())
                .region(Region.builder().regionId(req.getRegionId()).build())
                .build();
        return ProductMapper.toResponse(productService.create(p));
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @RequestBody UpdateProductRequest req) {
        Product p = Product.builder()
                .productName(req.getProductName())
                .price(req.getPrice())
                .productDescription(req.getProductDescription())
                .productImage(req.getProductImage())
                .category(req.getCategoryId() != null ? Category.builder().categoryId(req.getCategoryId()).build() : null)
                .region(req.getRegionId() != null ? Region.builder().regionId(req.getRegionId()).build() : null)
                .build();
        return ProductMapper.toResponse(productService.update(id, p));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    // Optional: upload ảnh trực tiếp
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        // Sau này bạn tích hợp CloudinaryService hoặc local file save ở đây
        String fakeUrl = "https://fakecdn.local/" + file.getOriginalFilename();
        return ResponseEntity.ok(fakeUrl);
    }
}
