package com.example.isp.controller;


import com.example.isp.model.enums.Role;
import com.example.isp.service.OrderService;
import com.example.isp.service.StaffOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/staff/orders")
public class StaffOrderController {

    private final StaffOrderService staffOrderService;
    private final OrderService orderService; // dùng để huỷ đơn nếu cần

    // Lấy toàn bộ đơn hàng
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(staffOrderService.getAllOrders());
    }

    // Xác nhận đơn hàng (CONFIRM)
    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<?> confirmOrder(@PathVariable Long orderId) {
        staffOrderService.confirmOrder(orderId);
        return ResponseEntity.ok("Order confirmed successfully");
    }

    // Gán shipepr vào đơn
    @PutMapping("/{orderId}/assign/{shipperId}")
    public ResponseEntity<?> assignOrder(@PathVariable Long orderId, @PathVariable Long shipperId) {
        staffOrderService.assignShipper(orderId, shipperId);
        return ResponseEntity.ok("Shipper assigned successfully");
    }

    // Hủy đơn (staff có thể huy bất kỳ đơn nào chưa giao)
    @PutMapping("/{orderId}/cancle")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.cancleOrder(orderId, username, Role.STAFF);
        return ResponseEntity.ok("Order canceled by staff successfully");
    }
}
