package com.example.isp.service;

import com.example.isp.model.Checklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChecklistService {

    /**
     * Tạo mới một checklist item.
     */
    Checklist create(Checklist checklist);

    /**
     * Cập nhật thông tin checklist item theo ID.
     */
    Checklist update(Long id, Checklist checklist);

    /**
     * Lấy danh sách toàn bộ checklist items.
     */
    List<Checklist> list();

    /**
     * Lấy một checklist item theo ID.
     */
    Checklist get(Long id);

    /**
     * Xóa checklist item theo ID.
     */
    void delete(Long id);

    /**
     * Tìm kiếm checklist theo tên.
     */
    List<Checklist> searchByName(String keyword);

    /**
     * Lọc checklist theo tên với phân trang.
     */
    Page<Checklist> filter(String name, Pageable pageable);
}
