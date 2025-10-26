package com.example.isp.controller;

import com.example.isp.service.PayOSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/webhooks/payos")
@RequiredArgsConstructor
public class PayOSWebhookController {

    private final PayOSService payOSService;

    @PostMapping
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> payload) {
        payOSService.handlePaymentWebhook(payload);
        return ResponseEntity.ok("Webhook received");
    }
}
