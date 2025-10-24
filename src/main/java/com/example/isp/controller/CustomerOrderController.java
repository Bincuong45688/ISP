package com.example.isp.controller;

import com.example.isp.dto.response.OrderDetailResponse;
import com.example.isp.dto.response.OrderResponse;
import com.example.isp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer/orders")
public class CustomerOrderController {

    private final OrderService orderService;

    // Lấy danh sách đơn hàng của customer
    @GetMapping
    public List<OrderResponse> getMyOrders() {
        return orderService.getOrdersOfCurrentCustomer();
    }

    // Xem chi tiết đơn hàng
    @GetMapping("/{orderId}")
    public OrderDetailResponse getOrderDetail(@PathVariable Long orderId) {
        return orderService.getOrderDetail(orderId);
    }

    // Hủy đơn hàng
    @PutMapping("/{orderId}/cancle")
    public ResponseEntity<?> cancleOrder(@PathVariable Long orderId) {
        orderService. cancleOrderOfCurrentCustomer(orderId);
        return ResponseEntity.ok("Order cancled successfully");
    }

}
