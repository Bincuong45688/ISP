package com.example.isp.controller;

import com.example.isp.service.PayOSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payos")
@RequiredArgsConstructor
@Slf4j
public class PayOSWebhookController {

    private final PayOSService payOSService;

    // PayOS có thể gọi HEAD/GET để probe webhook
    @GetMapping("/webhook")
    public ResponseEntity<String> probe() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody(required = false) String rawBody,
            @RequestHeader(value = "x-payos-signature", required = false) String signature
    ) {
        try {
            log.info("=== WEBHOOK RECEIVED FROM PAYOS ===");
            log.info("Signature = {}", signature);
            log.info("RawBody = {}", rawBody);

            payOSService.handlePaymentWebhookRaw(rawBody, signature);

        } catch (Exception ex) {
            log.error("Webhook processing error: {}", ex.getMessage(), ex);

            // Vẫn trả 200 OK để PayOS không gửi lại liên tục
            return ResponseEntity.ok("ERROR");
        }

        return ResponseEntity.ok("OK");
    }
}
