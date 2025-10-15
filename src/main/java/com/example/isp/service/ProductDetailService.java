package com.example.isp.service;

import com.example.isp.model.ProductDetail;
import java.util.List;

public interface ProductDetailService {

    /**
     * Tạo mới chi tiết sản phẩm (ProductDetail)
     * @param d đối tượng ProductDetail cần lưu
     * @return ProductDetail đã được lưu
     */
    ProductDetail create(ProductDetail d);

    /**
     * Cập nhật chi tiết sản phẩm theo ID
     * @param id ID của ProductDetail cần cập nhật
     * @param u thông tin mới
     * @return ProductDetail sau khi cập nhật
     */
    ProductDetail update(Long id, ProductDetail u);

    /**
     * Lấy danh sách chi tiết theo productId
     * @param productId ID sản phẩm cha
     * @return Danh sách ProductDetail thuộc sản phẩm đó
     */
    List<ProductDetail> byProduct(Long productId);

    /**
     * Xóa chi tiết sản phẩm theo ID
     * @param id ID của ProductDetail cần xóa
     */
    void delete(Long id);
}
