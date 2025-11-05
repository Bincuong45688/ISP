package com.example.isp.controller;

import com.example.isp.dto.response.OrderResponse;
import com.example.isp.service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shipper/orders")
public class ShipperOrderController {

    private final ShipperService shipperService;

    @PutMapping("/{orderId}/accept")
    public ResponseEntity<?> acceptOrder(@PathVariable Long orderId) {
        shipperService.acceptOrder(orderId);
        return ResponseEntity.ok("Order accepted successfully. Status: SHIPPING");
    }

    // SHIPPING -> COMPLETED (+ upload POD)
    @PutMapping(value = "/{orderId}/complete", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> completeOrder(
            @PathVariable Long orderId,
            @RequestPart("proofImage") MultipartFile proofImage
    ) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        shipperService.completeOrder(orderId, username, proofImage);
        return ResponseEntity.ok("Order marked as complete successfully");
    }

    @GetMapping("/pending")
    public ResponseEntity<List<OrderResponse>> getPendingOrders() {
        return ResponseEntity.ok(shipperService.getPendingOrders());
    }

    @GetMapping("/active")
    public ResponseEntity<List<OrderResponse>> getActiveOrders() {
        return ResponseEntity.ok(shipperService.getActiveOrders());
    }

    @GetMapping("/completed")
    public ResponseEntity<List<OrderResponse>> getCompletedOrders() {
        return ResponseEntity.ok(shipperService.getCompletedOrders());
    }
}
