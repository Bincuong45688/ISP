package com.example.isp.controller;


import com.example.isp.service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipper/orders")
public class ShipperOrderController {

    private final ShipperService shipperService;

    // Lấy danh sách đơn được giao cho Shipper
    @GetMapping
    public ResponseEntity<?> getOrdersAssignedToShipper() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.ok(shipperService.getOrdersByShipper(username));
    }

    // Cập nhật trạng thái hoàn tất đơn SHIPPING -> COMPLETED)
    @PutMapping("/{orderId}/complete")
    public ResponseEntity<?> completeOrder(@PathVariable Long orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        shipperService.completeOrder(orderId, username);
        return ResponseEntity.ok("Order marked as complete successfully");
    }

}
