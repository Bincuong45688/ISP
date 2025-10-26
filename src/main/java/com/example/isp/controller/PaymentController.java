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

    @PostMapping("/create/{orderId}")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Map<String, String>> createPayment(@PathVariable Long orderId) {
        return ResponseEntity.ok(payOSService.createPaymentLink(orderId));
    }
}
