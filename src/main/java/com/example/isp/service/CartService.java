package com.example.isp.service;

import com.example.isp.model.Cart;

public interface CartService {

    /**
     * Lấy giỏ hàng đang mở (ACTIVE) của khách hàng.
     * Nếu chưa có thì tự tạo mới.
     */
    Cart getOpenCart(Long customerId);

    /**
     * Thêm sản phẩm vào giỏ hàng.
     * - Nếu sản phẩm đã có: tăng số lượng.
     * - Nếu chưa có: thêm mới CartItem.
     */
    Cart addItem(Long customerId, Long productId, Integer quantity);

    /**
     * Giảm hoặc xóa sản phẩm khỏi giỏ hàng.
     * - Nếu quantity > 0: giảm số lượng.
     * - Nếu quantity == 0 hoặc <= hiện tại: xóa khỏi giỏ.
     */
    Cart removeItem(Long customerId, Long productId, Integer quantity);

    /**
     * Xóa toàn bộ sản phẩm trong giỏ hàng (đặt lại trống).
     */
    Cart clear(Long customerId);

    /**
     * Thanh toán (checkout) giỏ hàng:
     * - Đổi trạng thái sang CHECKED_OUT.
     * - Giữ lại thông tin CartItems để lưu lịch sử.
     */
    Cart checkout(Long customerId);
}
