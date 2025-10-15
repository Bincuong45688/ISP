package com.example.isp.controller;

import com.example.isp.dto.response.CategoryResponse;
import com.example.isp.dto.request.CreateCategoryRequest;
import com.example.isp.dto.request.UpdateCategoryRequest;
import com.example.isp.model.Category;
import com.example.isp.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryResponse> list() {
        return categoryService.list().stream().map(this::toRes).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(@Valid @RequestBody CreateCategoryRequest req) {
        Category c = Category.builder()
                .categoryName(req.getCategoryName())
                .description(req.getDescription())
                .build();
        return toRes(categoryService.create(c));
    }

    @PutMapping("/{id}")
    public CategoryResponse update(@PathVariable Long id, @RequestBody UpdateCategoryRequest req) {
        Category u = Category.builder()
                .categoryName(req.getCategoryName())
                .description(req.getDescription())
                .build();
        return toRes(categoryService.update(id, u));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        categoryService.delete(id);
    }

    private CategoryResponse toRes(Category c) {
        return CategoryResponse.builder()
                .categoryId(c.getCategoryId())
                .categoryName(c.getCategoryName())
                .description(c.getDescription())
                .build();
    }
}
