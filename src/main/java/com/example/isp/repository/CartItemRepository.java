package com.example.isp.repository;

import com.example.isp.model.Cart;
import com.example.isp.model.CartItem;
import org.springframework.data.jpa.repository.*;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @EntityGraph(attributePaths = "product")
    List<CartItem> findByCart(Cart cart);
}
