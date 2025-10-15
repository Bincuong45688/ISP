package com.example.isp.service;

import com.example.isp.model.Product;
import java.util.List;

public interface ProductService {

    /**
     * Tạo mới một sản phẩm.
     * @param p đối tượng Product cần lưu
     * @return Product sau khi được lưu
     */
    Product create(Product p);

    /**
     * Cập nhật thông tin sản phẩm theo ID.
     * @param id ID sản phẩm cần cập nhật
     * @param u thông tin mới
     * @return Product sau khi cập nhật
     */
    Product update(Long id, Product u);

    /**
     * Lấy danh sách toàn bộ sản phẩm.
     * @return Danh sách Product
     */
    List<Product> list();

    /**
     * Lấy danh sách sản phẩm theo Category ID.
     * @param categoryId ID của danh mục
     * @return Danh sách sản phẩm thuộc danh mục đó
     */
    List<Product> byCategory(Long categoryId);

    /**
     * Lấy danh sách sản phẩm theo Region ID.
     * @param regionId ID của vùng miền
     * @return Danh sách sản phẩm thuộc vùng đó
     */
    List<Product> byRegion(Long regionId);

    /**
     * Tìm kiếm sản phẩm theo tên (không phân biệt hoa thường).
     * @param q chuỗi tìm kiếm
     * @return Danh sách sản phẩm phù hợp
     */
    List<Product> search(String q);

    /**
     * Xóa sản phẩm theo ID.
     * @param id ID của sản phẩm cần xóa
     */
    void delete(Long id);
}
