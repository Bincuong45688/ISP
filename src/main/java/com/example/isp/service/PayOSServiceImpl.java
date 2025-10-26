package com.example.isp.service;

import com.example.isp.model.Order;
import com.example.isp.model.Payment;
import com.example.isp.model.enums.OrderStatus;
import com.example.isp.repository.OrderRepository;
import com.example.isp.repository.PaymentRepository;
import com.example.isp.service.PayOSService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.PaymentData;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PayOSServiceImpl implements PayOSService {

    private final PayOS payOS;
    private final OrderRepository orderRepo;
    private final PaymentRepository paymentRepo;

    @Override
    public Map<String, String> createPaymentLink(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng ID: " + orderId));

        BigDecimal total = order.getTotalAmount() == null ? BigDecimal.ZERO : order.getTotalAmount();
        int amountVnd = total.intValue();

        try {
            // Tạo request gửi đến PayOS
            PaymentData data = PaymentData.builder()
                    .orderCode(orderId)
                    .amount(amountVnd)
                    .description("Thanh toán đơn hàng #" + order.getOrderId())
                    .returnUrl("https://isp-frontend.vercel.app/checkout/success") // URL frontend khi thanh toán thành công
                    .cancelUrl("https://isp-frontend.vercel.app/checkout/cancel")  // URL frontend khi hủy
                    .build();

            CheckoutResponseData response = payOS.createPaymentLink(data);

            // Lưu thanh toán vào DB
            Payment payment = Payment.builder()
                    .order(order)
                    .status("PENDING")
                    .payosOrderCode(String.valueOf(orderId))
                    .amount(total)
                    .checkoutUrl(response.getCheckoutUrl())
                    .qrCodeUrl(response.getQrCode())
                    .createdAt(Instant.now())
                    .build();
            paymentRepo.save(payment);

            return Map.of(
                    "paymentUrl", response.getCheckoutUrl(),
                    "orderCode", String.valueOf(orderId)
            );

        } catch (Exception e) {
            log.error("Lỗi tạo link thanh toán PayOS: {}", e.getMessage());
            throw new RuntimeException("Không thể tạo link thanh toán PayOS");
        }
    }

    @Override
    public void handlePaymentWebhook(Map<String, Object> webhookData) {
        log.info("📩 Nhận webhook từ PayOS: {}", webhookData);

        Map<String, Object> data = (Map<String, Object>) webhookData.get("data");
        if (data == null || !data.containsKey("orderCode")) return;

        Long orderCode = ((Number) data.get("orderCode")).longValue();
        Boolean success = (Boolean) webhookData.get("success");

        Payment payment = paymentRepo.findByPayosOrderCode(String.valueOf(orderCode))
                .orElseThrow(() -> new RuntimeException("Không tìm thấy payment với orderCode: " + orderCode));

        Order order = payment.getOrder();

        if (Boolean.TRUE.equals(success)) {
            payment.setStatus("PAID");
            payment.setPaidAt(Instant.now());
            paymentRepo.save(payment);

            order.setStatus(OrderStatus.CONFIRMED);
            orderRepo.save(order);

            log.info("✅ Thanh toán thành công cho order {}", orderCode);
        } else {
            payment.setStatus("FAILED");
            paymentRepo.save(payment);

            order.setStatus(OrderStatus.CANCELLED);
            orderRepo.save(order);

            log.warn("❌ Thanh toán thất bại cho order {}", orderCode);
        }
    }
}
