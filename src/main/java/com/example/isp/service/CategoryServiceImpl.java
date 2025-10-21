package com.example.isp.service;

import com.example.isp.model.Category;
import com.example.isp.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category create(Category c) {
        return categoryRepository.save(c);
    }

    @Override
    public Category update(Long id, Category u) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + id));

        // Cập nhật dữ liệu mới
        existing.setCategoryName(u.getCategoryName());
        existing.setDescription(u.getDescription());

        // Lưu lại vào DB
        return categoryRepository.save(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> list() {
        return categoryRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new EntityNotFoundException("Category not found: " + id);
        }
        categoryRepository.deleteById(id);
    }
}
