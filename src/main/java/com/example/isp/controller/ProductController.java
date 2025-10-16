package com.example.isp.controller;

import com.example.isp.dto.request.CreateProductRequest;
import com.example.isp.dto.request.UpdateProductRequest;
import com.example.isp.dto.response.ProductResponse;
import com.example.isp.mapper.ProductMapper;
import com.example.isp.model.Category;
import com.example.isp.model.Product;
import com.example.isp.model.Region;
import com.example.isp.service.ProductService;
import com.example.isp.service.CloudinaryService; // <-- thêm

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal; // <-- thêm
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService; // <-- thêm

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

    // ==== 1) CREATE - JSON (giữ nếu muốn dùng URL trực tiếp) ====
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createJson(@Valid @RequestBody CreateProductRequest req) {
        Product p = Product.builder()
                .productName(req.getProductName())
                .price(req.getPrice())
                .productDescription(req.getProductDescription())
                .productImage(req.getProductImage()) // URL có sẵn
                .category(Category.builder().categoryId(req.getCategoryId()).build())
                .region(Region.builder().regionId(req.getRegionId()).build())
                .build();
        return ProductMapper.toResponse(productService.create(p));
    }

    // ==== 2) CREATE - multipart/form-data (upload file lên Cloudinary) ====
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createForm(
            @RequestParam String productName,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) String productDescription,
            @RequestParam Long categoryId,
            @RequestParam Long regionId,
            @RequestParam("file") MultipartFile file // Swagger sẽ hiện Choose file
    ) {
        // upload ảnh
        String imageUrl = cloudinaryService.uploadImage(file, "isp/products");

        Product p = Product.builder()
                .productName(productName)
                .price(price)
                .productDescription(productDescription)
                .productImage(imageUrl)
                .category(Category.builder().categoryId(categoryId).build())
                .region(Region.builder().regionId(regionId).build())
                .build();

        return ProductMapper.toResponse(productService.create(p));
    }

    // ==== 3) UPDATE - JSON (giữ nếu muốn cập nhật bằng URL) ====
    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ProductResponse updateJson(@PathVariable Long id, @RequestBody UpdateProductRequest req) {
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

    // ==== 4) UPDATE - multipart/form-data (ảnh file là tùy chọn) ====
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponse updateForm(
            @PathVariable Long id,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) BigDecimal price,
            @RequestParam(required = false) String productDescription,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long regionId,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        Product patch = Product.builder()
                .productName(productName)
                .price(price)
                .productDescription(productDescription)
                .category(categoryId != null ? Category.builder().categoryId(categoryId).build() : null)
                .region(regionId != null ? Region.builder().regionId(regionId).build() : null)
                .build();

        if (file != null && !file.isEmpty()) {
            String newUrl = cloudinaryService.uploadImage(file, "isp/products");
            patch.setProductImage(newUrl);
        }

        return ProductMapper.toResponse(productService.update(id, patch));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }
}
