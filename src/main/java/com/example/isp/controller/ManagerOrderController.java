package com.example.isp.controller;


import com.example.isp.dto.response.AssignShipperResponse;
import com.example.isp.dto.response.TopSellingProductResponse;
import com.example.isp.mapper.OrderMapper;
import com.example.isp.model.Order;
import com.example.isp.model.enums.Role;
import com.example.isp.service.OrderService;
import com.example.isp.service.ProductReportService;
import com.example.isp.service.ManagerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/manager/orders")
public class ManagerOrderController {

    private final ManagerOrderService managerOrderService;
    private final ProductReportService productReportService;
    private final OrderMapper orderMapper;
    private final OrderService orderService; // dùng để huỷ đơn nếu cần

    // Lấy toàn bộ đơn hàng
    @GetMapping
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok(managerOrderService.getAllOrders());
    }

    // Xác nhận đơn hàng (CONFIRM)
    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<?> confirmOrder(@PathVariable Long orderId) {
        managerOrderService.confirmOrder(orderId);
        return ResponseEntity.ok("Order confirmed successfully");
    }

    // Gán shipepr vào đơn
    @PutMapping("/{orderId}/assign/{shipperId}")
    public ResponseEntity<AssignShipperResponse> assignOrder(@PathVariable Long orderId, @PathVariable Long shipperId) {
        Order order = managerOrderService.assignShipper(orderId, shipperId);
        return ResponseEntity.ok(orderMapper.toAssignResponse(order));
    }

    // Hủy đơn (manager có thể huy bất kỳ đơn nào chưa giao)
    @PutMapping("/{orderId}/cancle")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        orderService.cancleOrder(orderId, username, Role.MANAGER);
        return ResponseEntity.ok("Order canceled by manager successfully");
    }

    @GetMapping("/top-selling")
    public ResponseEntity<?> getTopSellingProducts() {
        List<TopSellingProductResponse> list = productReportService.getTopSellingProducts();
        Map<String, Object> response = new HashMap<>();
        response.put("success", true);
        response.put("message", "Lấy danh sách sản phẩm bán chạy thành công");
        response.put("data", list);
        response.put("total", list.size());
        response.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

}
