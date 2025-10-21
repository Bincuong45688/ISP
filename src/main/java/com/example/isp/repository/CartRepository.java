package com.example.isp.repository;

import com.example.isp.model.Cart;
import com.example.isp.model.Customer;
import com.example.isp.model.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByCustomerAndCartStatus(Customer customer, CartStatus cartStatus);
}
