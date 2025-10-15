package com.example.isp.controller;

import com.example.isp.dto.request.AddToCartRequest;
import com.example.isp.dto.response.CartItemResponse;
import com.example.isp.dto.response.CartResponse;
import com.example.isp.model.Cart;
import com.example.isp.repository.CartItemRepository;
import com.example.isp.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CartItemRepository cartItemRepository;

    @GetMapping("/{customerId}")
    public CartResponse getOpenCart(@PathVariable Long customerId) {
        return toCartResponse(cartService.getOpenCart(customerId));
    }

    @PostMapping("/{customerId}/add")
    public CartResponse add(
            @PathVariable Long customerId,
            @Valid @RequestBody AddToCartRequest req) {
        Cart cart = cartService.addItem(customerId, req.getProductId(), req.getQuantity());
        return toCartResponse(cart);
    }

    @PostMapping("/{customerId}/remove")
    public CartResponse remove(
            @PathVariable Long customerId,
            @Valid @RequestBody AddToCartRequest req) {
        Cart cart = cartService.removeItem(customerId, req.getProductId(), req.getQuantity());
        return toCartResponse(cart);
    }

    @PostMapping("/{customerId}/clear")
    public CartResponse clear(@PathVariable Long customerId) {
        return toCartResponse(cartService.clear(customerId));
    }

    @PostMapping("/{customerId}/checkout")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse checkout(@PathVariable Long customerId) {
        return toCartResponse(cartService.checkout(customerId));
    }

    private CartResponse toCartResponse(Cart cart) {
        var items = cartItemRepository.findByCart_CartId(cart.getCartId())
                .stream()
                .map(ci -> CartItemResponse.builder()
                        .productId(ci.getProduct().getProductId())
                        .productName(ci.getProduct().getProductName())
                        .quantity(ci.getQuantity())
                        .selected(ci.getSelected())
                        .build())
                .toList();

        return CartResponse.builder()
                .cartId(cart.getCartId())
                .cartStatus(cart.getCartStatus())
                .customerId(cart.getCustomer().getCustomerId())
                .customerName(cart.getCustomer().getCustomerName())
                .items(items)
                .build();
    }
}
