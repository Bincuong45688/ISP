package com.example.isp.service;

import com.example.isp.model.*;
import com.example.isp.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;

    @Override
    public Cart getOpenCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));

        return cartRepository.findByCustomerAndCartStatus(customer, "OPEN")
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .customer(customer)
                                .cartStatus("OPEN")
                                .build()
                ));
    }

    @Override
    public Cart addItem(Long customerId, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }

        Cart cart = getOpenCart(customerId);

        CartItem item = cartItemRepository
                .findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId)
                .orElseGet(() -> {
                    CartItem ci = new CartItem();
                    ci.setCart(cart);
                    Product p = new Product();
                    p.setProductId(productId); // chỉ set FK, không load toàn bộ entity
                    ci.setProduct(p);
                    ci.setQuantity(0);
                    ci.setSelected(true);
                    return ci;
                });

        item.setQuantity(item.getQuantity() + quantity);
        cartItemRepository.save(item);
        return cart;
    }

    @Override
    public Cart removeItem(Long customerId, Long productId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be > 0");
        }

        Cart cart = getOpenCart(customerId);

        CartItem item = cartItemRepository
                .findByCart_CartIdAndProduct_ProductId(cart.getCartId(), productId)
                .orElseThrow(() -> new EntityNotFoundException("Cart item not found"));

        int newQty = item.getQuantity() - quantity;
        if (newQty <= 0) {
            cartItemRepository.delete(item);
        } else {
            item.setQuantity(newQty);
            cartItemRepository.save(item);
        }
        return cart;
    }

    @Override
    public Cart clear(Long customerId) {
        Cart cart = getOpenCart(customerId);
        cartItemRepository.deleteByCart_CartId(cart.getCartId());
        return cart;
    }

    @Override
    public Cart checkout(Long customerId) {
        Cart cart = getOpenCart(customerId);
        List<CartItem> items = cartItemRepository.findByCart_CartId(cart.getCartId());

        if (items.isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        cart.setCartStatus("CHECKED_OUT");
        return cartRepository.save(cart);
    }
}
