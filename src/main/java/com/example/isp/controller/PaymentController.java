package com.example.isp.controller;

import com.example.isp.service.PayOSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PayOSService payOSService;

    // ================== TẠO LINK THANH TOÁN ==================
    @PostMapping("/create/{orderId}")
    @PreAuthorize("hasAnyAuthority('ROLE_CUSTOMER','CUSTOMER')")
    public ResponseEntity<Map<String, String>> createPayment(@PathVariable Long orderId) {
        Map<String, String> result = payOSService.createPaymentLink(orderId);
        return ResponseEntity.ok(result);
    }


    // ================== USER CANCEL ==================
    @PostMapping("/cancel/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> cancelPayment(@PathVariable Long orderId) {
        payOSService.userCancel(orderId);
        return ResponseEntity.ok().build();
    }
}
