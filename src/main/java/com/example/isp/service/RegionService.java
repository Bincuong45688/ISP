package com.example.isp.service;

import com.example.isp.model.Region;
import java.util.List;

public interface RegionService {

    /**
     * Tạo mới một vùng miền.
     * @param r đối tượng Region cần lưu
     * @return Region sau khi được lưu
     */
    Region create(Region r);

    /**
     * Cập nhật thông tin vùng miền theo ID.
     * @param id ID vùng cần cập nhật
     * @param u dữ liệu mới
     * @return Region sau khi được cập nhật
     */
    Region update(Long id, Region u);

    /**
     * Lấy danh sách toàn bộ vùng miền.
     * @return Danh sách Region
     */
    List<Region> list();

    /**
     * Xóa vùng miền theo ID.
     * @param id ID của vùng cần xóa
     */
    void delete(Long id);
}
