package com.example.isp.controller;


import com.example.isp.dto.response.OrderResponse;
import com.example.isp.service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipper/orders")
public class ShipperOrderController {

    private final ShipperService shipperService;

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<?> acceptOrder(@PathVariable Long orderId) {
        shipperService.acceptOrder(orderId);
        return  ResponseEntity.ok("Order accepted successfully. Status: SHIPPING");
    }

    // Cập nhật trạng thái hoàn tất đơn SHIPPING -> COMPLETED)
    @PutMapping("/{orderId}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        shipperService.completeOrder(orderId, username);
        return ResponseEntity.ok("Order marked as complete successfully");
    }

    // Lấy các đơn đang chờ shipper xác nhận
    @GetMapping("/pending")
    public ResponseEntity<List<OrderResponse>> getPendingOrders() {
        return ResponseEntity.ok(shipperService.getPendingOrders());
    }

    // Lấy các đơn đang giao
    @GetMapping("/active")
    public ResponseEntity<List<OrderResponse>> getActiveOrders() {
        return ResponseEntity.ok(shipperService.getActiveOrders());
    }

    // Lấy các đơn đã hoàn thành
    @GetMapping("/completed")
    public ResponseEntity<List<OrderResponse>> getCompletedOrders() {
        return ResponseEntity.ok(shipperService.getCompletedOrders());
    }
}
