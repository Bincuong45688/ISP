package com.example.isp.service;

import java.util.Map;

public interface PayOSService {
    Map<String, String> createPaymentLink(Long orderId);
    void handlePaymentWebhook(Map<String, Object> webhookData);
}
