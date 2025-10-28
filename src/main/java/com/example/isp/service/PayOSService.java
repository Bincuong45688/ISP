package com.example.isp.service;

import java.util.Map;

public interface PayOSService {

    /** Tạo liên kết thanh toán cho đơn hàng. */
    Map<String, String> createPaymentLink(Long orderId);

    /** Xử lý webhook PayOS (raw JSON + header chữ ký). */
    void handlePaymentWebhookRaw(String rawBody, String headerSignature);
}
