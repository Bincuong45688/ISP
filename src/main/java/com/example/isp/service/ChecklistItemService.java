package com.example.isp.service;

import com.example.isp.model.ChecklistItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChecklistItemService {

    /**
     * Tạo mới một checklist item.
     */
    ChecklistItem create(ChecklistItem checklistItem);

    /**
     * Cập nhật thông tin checklist item theo ID.
     */
    ChecklistItem update(Long id, ChecklistItem checklistItem);

    /**
     * Lấy danh sách toàn bộ checklist items.
     */
    List<ChecklistItem> list();

    /**
     * Lấy một checklist item theo ID.
     */
    ChecklistItem get(Long id);

    /**
     * Xóa checklist item theo ID.
     */
    void delete(Long id);

    /**
     * Lấy tất cả items của một ritual.
     */
    List<ChecklistItem> getByRitualId(Long ritualId);

    /**
     * Lấy tất cả items của một checklist.
     */
    List<ChecklistItem> getByChecklistId(Long itemId);

    /**
     * Lọc checklist items theo ritual và checklist với phân trang.
     */
    Page<ChecklistItem> filter(Long ritualId, Long itemId, Pageable pageable);
}
