package com.example.isp.controller;

import com.example.isp.dto.request.AddToCartRequest;
import com.example.isp.dto.request.AdjustCartItemRequest;
import com.example.isp.dto.response.CartResponse;
import com.example.isp.repository.CustomerRepository;
import com.example.isp.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final CustomerRepository customerRepository;

    // === Helper: Lấy customerId từ JWT token ===
    private Long currentCustomerId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new IllegalStateException("Không xác định được người dùng từ token.");
        }

        String username = auth.getName();
        return customerRepository.findIdByAccountUsername(username)
                .orElseThrow(() -> new IllegalStateException("Không tìm thấy customerId cho username: " + username));
    }

    // === Lấy giỏ hàng của khách hàng đang đăng nhập ===
    @GetMapping
    public CartResponse getCart() {
        return cartService.getCart(currentCustomerId());
    }

    // === Thêm sản phẩm vào giỏ ===
    @PostMapping("/items")
    @ResponseStatus(HttpStatus.CREATED)
    public CartResponse addItem(@Valid @RequestBody AddToCartRequest req) {
        return cartService.addItem(currentCustomerId(), req);
    }

    // === Xóa 1 sản phẩm khỏi giỏ ===
    @PostMapping("/items/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(@RequestParam Long productId) {
        cartService.removeItem(currentCustomerId(), productId);
    }

    // === Giảm 1 sản phẩm trong giỏ ===
    @PostMapping("/items/decrease")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse decreaseItem(@RequestParam Long productId) {
        return cartService.decreaseItem(currentCustomerId(), productId, 1);
    }
    // Tăng 1 sản phẩm trong giỏ
    @PostMapping("/items/increase")
    public CartResponse increaseItem(@RequestParam Long productId) {
        return cartService.increaseItem(currentCustomerId(), productId, 1);
    }

    // === Xóa toàn bộ giỏ ===
    @PostMapping("/clear")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart() {
        cartService.clearCart(currentCustomerId());
    }

    // === Apply voucher vào giỏ hàng ===
    @PostMapping("/apply-voucher")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse applyVoucher(@RequestParam String voucherCode) {
        return cartService.applyVoucher(currentCustomerId(), voucherCode);
    }

    // === Remove voucher khỏi giỏ hàng ===
    @PostMapping("/remove-voucher")
    @ResponseStatus(HttpStatus.OK)
    public CartResponse removeVoucher() {
        return cartService.removeVoucher(currentCustomerId());
    }
}
