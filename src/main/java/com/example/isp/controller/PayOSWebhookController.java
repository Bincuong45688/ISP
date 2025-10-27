package com.example.isp.controller;

import com.example.isp.service.PayOSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payos")
@RequiredArgsConstructor
public class PayOSWebhookController {

    private final PayOSService payOSService;

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(@RequestBody Map<String, Object> webhookData) {
        // Gọi service xử lý webhook
        payOSService.handlePaymentWebhook(webhookData);
        // Trả phản hồi về cho PayOS (PayOS yêu cầu phản hồi 200 OK)
        return ResponseEntity.ok("Webhook received successfully");
    }
}
