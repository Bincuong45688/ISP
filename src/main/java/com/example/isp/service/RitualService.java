package com.example.isp.service;

import com.example.isp.model.Ritual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RitualService {

    /**
     * Tạo mới một nghi lễ.
     * @param ritual đối tượng Ritual cần lưu
     * @return Ritual sau khi được lưu
     */
    Ritual create(Ritual ritual);

    /**
     * Cập nhật thông tin nghi lễ theo ID.
     * @param id ID nghi lễ cần cập nhật
     * @param ritual dữ liệu mới
     * @return Ritual sau khi được cập nhật
     */
    Ritual update(Long id, Ritual ritual);

    /**
     * Lấy danh sách toàn bộ nghi lễ.
     * @return Danh sách Ritual
     */
    List<Ritual> list();

    /**
     * Lấy một nghi lễ theo ID.
     * @param id ID của nghi lễ
     * @return Ritual
     */
    Ritual get(Long id);

    /**
     * Lấy một nghi lễ theo ID kèm theo danh sách checklists.
     * @param id ID của nghi lễ
     * @return Ritual với checklists
     */
    Ritual getWithChecklists(Long id);

    /**
     * Xóa nghi lễ theo ID.
     * @param id ID của nghi lễ cần xóa
     */
    void delete(Long id);

    /**
     * Tìm kiếm nghi lễ theo tên.
     * @param keyword từ khóa tìm kiếm
     * @return Danh sách Ritual
     */
    List<Ritual> searchByName(String keyword);

    /**
     * Lọc nghi lễ theo tên và vùng miền với phân trang.
     * @param name tên nghi lễ (tìm kiếm gần đúng)
     * @param regionId ID vùng miền
     * @param pageable thông tin phân trang
     * @return Page<Ritual>
     */
    Page<Ritual> filter(String name, Long regionId, Pageable pageable);
}
