package com.example.isp.controller;

import com.example.isp.dto.response.ProductResponse;
import com.example.isp.mapper.ProductMapper;
import com.example.isp.model.Category;
import com.example.isp.model.Product;
import com.example.isp.model.Region;
import com.example.isp.service.CloudinaryService;
import com.example.isp.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CloudinaryService cloudinaryService;

    // ==== List ====
    @GetMapping
    public List<ProductResponse> list() {
        return productService.list().stream().map(ProductMapper::toResponse).toList();
    }

    // ==== Get by id ====
    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable Long id) {
        return ProductMapper.toResponse(productService.get(id));
    }

    // ==== Create (multipart/form-data) ====
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(
            @RequestParam String productName,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) String productDescription,
            @RequestParam Long categoryId,
            @RequestParam Long regionId,
            @RequestParam("file") MultipartFile file
    ) {
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

    // ==== Update (multipart/form-data) ====
    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProductResponse update(
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

    // ==== Delete ====
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    // ==== Search theo tên duy nhất ====
    @GetMapping("/search")
    public List<ProductResponse> search(@RequestParam String q) {
        return productService.searchByName(q).stream().map(ProductMapper::toResponse).toList();
    }

    // ==== BỘ LỌC vùng–loại–giá (TỰ ĐẶT MẶC ĐỊNH TRONG CODE) ====
    // Gọi đơn giản: /api/products/filter?regionId=1&categoryId=3&minPrice=0&maxPrice=10000000
    @GetMapping("/filter")
    public Page<ProductResponse> filter(
            @RequestParam(required = false) Long regionId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice
    ) {
        // ===== Thiết lập mặc định ngay trong code =====
        final int defaultPage = 0;             // luôn bắt đầu từ trang đầu
        final int defaultSize = 12;            // 12 sản phẩm mỗi trang
        final String defaultSortField = "productId";  // sắp xếp theo ID
        final Sort.Direction defaultSortDir = Sort.Direction.DESC; // mới nhất lên trước

        Pageable pageable = PageRequest.of(defaultPage, defaultSize, Sort.by(defaultSortDir, defaultSortField));

        return productService
                .filter(regionId, categoryId, minPrice, maxPrice, pageable)
                .map(ProductMapper::toResponse);
    }
}
