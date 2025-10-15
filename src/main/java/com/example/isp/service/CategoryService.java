package com.example.isp.service;

import com.example.isp.model.Category;
import java.util.List;

public interface CategoryService {

    /**
     * Tạo mới một danh mục.
     * @param c đối tượng Category cần lưu
     * @return Category đã được lưu trong DB
     */
    Category create(Category c);

    /**
     * Cập nhật thông tin danh mục theo ID.
     * @param id ID của danh mục cần cập nhật
     * @param u thông tin mới
     * @return Category sau khi được cập nhật
     */
    Category update(Long id, Category u);

    /**
     * Lấy danh sách toàn bộ danh mục hiện có.
     * @return List<Category>
     */
    List<Category> list();

    /**
     * Xóa một danh mục theo ID.
     * @param id ID của danh mục cần xóa
     */
    void delete(Long id);
}
