package com.example.isp.controller;

import com.example.isp.service.PayOSService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payos")
@RequiredArgsConstructor
public class PayOSWebhookController {

    private final PayOSService payOSService;

    // PayOS “probe” có thể gọi GET/HEAD -> bạn đã mở Security rồi.
    @GetMapping("/webhook")
    public ResponseEntity<String> probe() {
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> handleWebhook(
            @RequestBody(required = false) String rawBody,
            @RequestHeader(value = "X-Signature", required = false) String signature) {
        // Tuyệt đối KHÔNG throw ra ngoài, luôn trả 200 OK
        payOSService.handlePaymentWebhookRaw(rawBody, signature);
        return ResponseEntity.ok("OK");
    }
}

