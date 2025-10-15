package com.example.isp.repository;

import com.example.isp.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    // Lấy toàn bộ item theo Cart ID
    List<CartItem> findByCart_CartId(Long cartId);

    // Kiểm tra 1 sản phẩm cụ thể có nằm trong giỏ hàng hay không
    Optional<CartItem> findByCart_CartIdAndProduct_ProductId(Long cartId, Long productId);

    // Xóa tất cả item theo giỏ hàng
    void deleteByCart_CartId(Long cartId);
}
