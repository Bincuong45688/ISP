package com.example.isp.controller;

import com.example.isp.dto.request.CreateProductRequest;
import com.example.isp.dto.request.UpdateProductRequest;
import com.example.isp.dto.response.ProductResponse;
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

    private final ProductService service;

    @GetMapping
    public List<ProductResponse> list() {
        return service.list();
    }

    @GetMapping("/{id}")
    public ProductResponse get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping("/search")
    public List<ProductResponse> search(@RequestParam String q) {
        return service.search(q);
    }

    @GetMapping("/by-region/{regionId}")
    public List<ProductResponse> byRegion(@PathVariable Long regionId) {
        return service.byRegion(regionId);
    }

    @GetMapping("/by-category/{categoryId}")
    public List<ProductResponse> byCategory(@PathVariable Long categoryId) {
        return service.byCategory(categoryId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse create(@Valid @RequestBody CreateProductRequest req) {
        return service.create(req);
    }

    @PutMapping("/{id}")
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody UpdateProductRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
