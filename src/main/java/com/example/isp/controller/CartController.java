package com.example.isp.controller;

import com.example.isp.dto.request.AddToCartRequest;
import com.example.isp.dto.response.CartItemResponse;
import com.example.isp.dto.response.CartResponse;
import com.example.isp.model.Cart;
import com.example.isp.repository.CartItemRepository;
import com.example.isp.service.CartService;
import com.example.isp.util.SecurityUtil;
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
    private final SecurityUtil securityUtil;

    // Lấy giỏ của chính user đang đăng nhập
    @GetMapping
    public CartResponse getMyOpenCart() {
        Long customerId = securityUtil.currentCustomerId();
        return toCartResponse(cartService.getOpenCart(customerId));
    }

    // Thêm sản phẩm vào giỏ
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse add(@Valid @RequestBody AddToCartRequest req) {
        Long customerId = securityUtil.currentCustomerId();
        Cart cart = cartService.addItem(customerId, req.getProductId(), req.getQuantity());
        return toCartResponse(cart);
    }

    // Giảm số lượng / xoá sản phẩm (tuỳ logic trong service)
    @PostMapping("/items/remove")
    public CartResponse remove(@Valid @RequestBody AddToCartRequest req) {
        Long customerId = securityUtil.currentCustomerId();
        Cart cart = cartService.removeItem(customerId, req.getProductId(), req.getQuantity());
        return toCartResponse(cart);
    }

    // Xoá sạch giỏ
    @PostMapping("/clear")
    public CartResponse clear() {
        Long customerId = securityUtil.currentCustomerId();
        return toCartResponse(cartService.clear(customerId));
    }

    // Checkout giỏ hiện tại
    @PostMapping("/checkout")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse checkout() {
        Long customerId = securityUtil.currentCustomerId();
        return toCartResponse(cartService.checkout(customerId));
    }

    // Mapper: Cart -> CartResponse
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
                .cartStatus(cart.getCartStatus().name())
                .customerId(cart.getCustomer().getCustomerId())
                .customerName(cart.getCustomer().getCustomerName())
                .items(items)
                .build();

    }
}
