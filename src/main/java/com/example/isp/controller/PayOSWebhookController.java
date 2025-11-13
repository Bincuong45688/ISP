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

    // PayOS probe GET webhook
    @GetMapping("/webhook")
    public ResponseEntity<String> probe() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody(required = false) String rawBody,
            @RequestHeader(value = "X-Signature", required = false) String signature) {

        log.info("=== WEBHOOK RECEIVED FROM PAYOS ===");
        log.info("Signature: {}", signature);
        log.info("Body: {}", rawBody);

        payOSService.handlePaymentWebhookRaw(rawBody, signature);

        return ResponseEntity.ok("OK");
    }
}
