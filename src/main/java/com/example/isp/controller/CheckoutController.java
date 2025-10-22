package com.example.isp.controller;

import com.example.isp.dto.request.CheckoutRequest;
import com.example.isp.dto.response.CheckoutResponse;
import com.example.isp.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    /**
     * API thực hiện quá trình checkout:
     * - Lấy giỏ hàng đang ACTIVE của user đăng nhập
     * - Kiểm tra các sản phẩm được chọn
     * - Tạo Order + OrderDetail tương ứng
     * - Đánh dấu giỏ hàng CHECKED_OUT
     */

    @PostMapping
    public ResponseEntity<CheckoutResponse> checkout(@RequestBody CheckoutRequest request) {
        CheckoutResponse response = checkoutService.checkout(request);
        return ResponseEntity.ok(response);
    }
}
