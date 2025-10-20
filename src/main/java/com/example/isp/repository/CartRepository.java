package com.example.isp.repository;

import com.example.isp.model.Cart;
import com.example.isp.model.enums.CartStatus;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @EntityGraph(attributePaths = "customer")
    Optional<Cart> findWithCustomerByCustomer_CustomerIdAndCartStatus(Long customerId, CartStatus status);

}
