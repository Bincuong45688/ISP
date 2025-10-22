package com.example.isp.service;

import com.example.isp.model.ChecklistItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChecklistItemService {

    /**
     * Tạo mới một vật phẩm.
     * @param item đối tượng ChecklistItem cần lưu
     * @return ChecklistItem sau khi được lưu
     */
    ChecklistItem create(ChecklistItem item);

    /**
     * Cập nhật thông tin vật phẩm theo ID.
     * @param id ID vật phẩm cần cập nhật
     * @param item dữ liệu mới
     * @return ChecklistItem sau khi được cập nhật
     */
    ChecklistItem update(Long id, ChecklistItem item);

    /**
     * Lấy danh sách toàn bộ vật phẩm.
     * @return Danh sách ChecklistItem
     */
    List<ChecklistItem> list();

    /**
     * Lấy một vật phẩm theo ID.
     * @param id ID của vật phẩm
     * @return ChecklistItem
     */
    ChecklistItem get(Long id);

    /**
     * Xóa vật phẩm theo ID.
     * @param id ID của vật phẩm cần xóa
     */
    void delete(Long id);

    /**
     * Tìm kiếm vật phẩm theo tên.
     * @param keyword từ khóa tìm kiếm
     * @return Danh sách ChecklistItem
     */
    List<ChecklistItem> searchByName(String keyword);

    /**
     * Lọc vật phẩm theo tên với phân trang.
     * @param name tên vật phẩm (tìm kiếm gần đúng)
     * @param pageable thông tin phân trang
     * @return Page<ChecklistItem>
     */
    Page<ChecklistItem> filter(String name, Pageable pageable);
}
