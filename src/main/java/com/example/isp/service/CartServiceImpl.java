package com.example.isp.service;

import com.example.isp.dto.request.AddToCartRequest;
import com.example.isp.dto.response.*;
import com.example.isp.model.*;
import com.example.isp.model.enums.CartStatus;
import com.example.isp.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;

    // === Lấy hoặc tạo giỏ hàng đang mở ===
    private Cart getOpenCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));

        return cartRepository.findByCustomerAndCartStatus(customer, CartStatus.OPEN)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .customer(customer)
                                .cartStatus(CartStatus.OPEN)
                                .build()
                ));
    }

    // === Lấy giỏ hàng hiện tại ===
    @Override
    public CartResponse getCart(Long customerId) {
        Cart cart = getOpenCart(customerId);
        List<CartItem> items = cartItemRepository.findByCart(cart);
        return toCartResponse(cart, items);
    }

    // === Thêm sản phẩm vào giỏ ===
    @Override
    public CartResponse addItem(Long customerId, AddToCartRequest req) {
        Cart cart = getOpenCart(customerId);
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + req.getProductId()));

        CartItem item = cartItemRepository.findByCart(cart).stream()
                .filter(i -> i.getProduct().getProductId().equals(product.getProductId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    newItem.setSelected(true);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + req.getQuantity());
        item.setSelected(true);
        cartItemRepository.save(item);

        return toCartResponse(cart, cartItemRepository.findByCart(cart));
    }

    // === Xóa 1 sản phẩm khỏi giỏ ===
    @Override
    public void removeItem(Long customerId, Long productId) {
        Cart cart = getOpenCart(customerId);
        cartItemRepository.findByCart(cart).stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .findFirst()
                .ifPresent(cartItemRepository::delete);
    }

    // === Xóa toàn bộ giỏ ===
    @Override
    public void clearCart(Long customerId) {
        Cart cart = getOpenCart(customerId);
        cartItemRepository.deleteAll(cartItemRepository.findByCart(cart));
    }

    // === Mapper trả về dữ liệu đầy đủ cho FE ===
    private CartResponse toCartResponse(Cart cart, List<CartItem> items) {
        List<CartItemResponse> itemResponses = items.stream().map(i -> {
            BigDecimal price = i.getProduct().getPrice(); // BigDecimal
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(i.getQuantity()));

            return CartItemResponse.builder()
                    .cartItemId(i.getCartItemId())
                    .productId(i.getProduct().getProductId())
                    .productName(i.getProduct().getProductName())
                    .productImage(i.getProduct().getProductImage())
                    .unitPrice(price)
                    .quantity(i.getQuantity())
                    .lineTotal(lineTotal)
                    .selected(Boolean.TRUE.equals(i.getSelected()))
                    .build();
        }).toList();

        BigDecimal subTotal = itemResponses.stream()
                .map(CartItemResponse::getLineTotal)
                .reduce(ZERO, BigDecimal::add);

        return CartResponse.builder()
                .cartId(cart.getCartId())
                .cartStatus(cart.getCartStatus().name())
                .customerId(cart.getCustomer().getCustomerId())
                .customerName(cart.getCustomer().getCustomerName())
                .items(itemResponses)
                .totalItems(itemResponses.size())
                .subTotal(subTotal)
                .currency("VND")
                .build();
    }
}
