package com.example.isp.service;

import com.example.isp.model.Checklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChecklistService {

    /**
     * Tạo mới một checklist.
     * @param checklist đối tượng Checklist cần lưu
     * @return Checklist sau khi được lưu
     */
    Checklist create(Checklist checklist);

    /**
     * Cập nhật thông tin checklist theo ID.
     * @param id ID checklist cần cập nhật
     * @param checklist dữ liệu mới
     * @return Checklist sau khi được cập nhật
     */
    Checklist update(Long id, Checklist checklist);

    /**
     * Lấy danh sách toàn bộ checklist.
     * @return Danh sách Checklist
     */
    List<Checklist> list();

    /**
     * Lấy một checklist theo ID.
     * @param id ID của checklist
     * @return Checklist
     */
    Checklist get(Long id);

    /**
     * Xóa checklist theo ID.
     * @param id ID của checklist cần xóa
     */
    void delete(Long id);

    /**
     * Lấy danh sách checklist theo ritual ID.
     * @param ritualId ID của ritual
     * @return Danh sách Checklist
     */
    List<Checklist> getByRitualId(Long ritualId);

    /**
     * Lấy danh sách checklist theo item ID.
     * @param itemId ID của item
     * @return Danh sách Checklist
     */
    List<Checklist> getByItemId(Long itemId);

    /**
     * Lọc checklist theo ritual ID và item ID với phân trang.
     * @param ritualId ID của ritual
     * @param itemId ID của item
     * @param pageable thông tin phân trang
     * @return Page<Checklist>
     */
    Page<Checklist> filter(Long ritualId, Long itemId, Pageable pageable);
}
