package com.example.isp.repository;

import com.example.isp.model.Cart;
import com.example.isp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    // Tìm giỏ hàng của 1 khách theo trạng thái (ví dụ ACTIVE, CHECKED_OUT, ...)
    Optional<Cart> findByCustomerAndCartStatus(Customer customer, String cartStatus);
}
