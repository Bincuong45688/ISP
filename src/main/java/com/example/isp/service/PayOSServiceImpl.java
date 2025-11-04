package com.example.isp.service;

import com.example.isp.model.Order;
import com.example.isp.model.Payment;
import com.example.isp.model.enums.PaymentStatus;
import com.example.isp.repository.OrderRepository;
import com.example.isp.repository.PaymentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.exception.PayOSException;
import vn.payos.type.PaymentData;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayOSServiceImpl implements PayOSService {

    private final PayOS payOS;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.payments.successUrl}")
    private String successUrl;

    @Value("${app.payments.cancelUrl}")
    private String cancelUrl;

    @Override
    public Map<String, String> createPaymentLink(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng #" + orderId));

        var pendingOpt = paymentRepository
                .findTopByOrder_OrderIdAndStatusOrderByIdDesc(orderId, PaymentStatus.PENDING);
        if (pendingOpt.isPresent() && pendingOpt.get().getCheckoutUrl() != null) {
            Map<String, String> reuse = new HashMap<>();
            reuse.put("checkoutUrl", pendingOpt.get().getCheckoutUrl());
            reuse.put("orderCode", "ORD" + order.getOrderId());
            reuse.put("amount", order.getTotalAmount().toPlainString());
            return reuse;
        }

        long attempt = paymentRepository.countByOrder_OrderId(orderId) + 1;
        long payosOrderCode = order.getOrderId()  + attempt;

        long orderCodeForPayOS = payosOrderCode;
        int amountForPayOS = safeInt(order.getTotalAmount().longValue(), "amount");

        String cancelUrlFull = cancelUrl + "?orderId=" + orderId + "&code=" + payosOrderCode;

        PaymentData paymentData = PaymentData.builder()
                .orderCode(orderCodeForPayOS)
                .amount(amountForPayOS)
                .description("Thanh toán đơn hàng #" + orderId)
                .returnUrl(successUrl)
                .cancelUrl(cancelUrlFull)
                .build();

        try {
            var response = payOS.createPaymentLink(paymentData);

            Payment payment = Payment.builder()
                    .order(order)
                    .provider("PAYOS")
                    .status(PaymentStatus.PENDING)
                    .amount(order.getTotalAmount())
                    .payosOrderCode(payosOrderCode)
                    .checkoutUrl(response.getCheckoutUrl())
                    .createdAt(OffsetDateTime.now())
                    .build();
            paymentRepository.save(payment);

            Map<String, String> result = new HashMap<>();
            result.put("checkoutUrl", response.getCheckoutUrl());
            result.put("orderCode", "ORD" + order.getOrderId());
            result.put("amount", order.getTotalAmount().toPlainString());
            return result;

        } catch (PayOSException ex) {
            return paymentRepository.findByPayosOrderCode(payosOrderCode)
                    .map(p -> {
                        Map<String, String> m = new HashMap<>();
                        m.put("checkoutUrl", p.getCheckoutUrl());
                        m.put("orderCode", "ORD" + order.getOrderId());
                        m.put("amount", order.getTotalAmount().toPlainString());
                        return m;
                    })
                    .orElseThrow(() -> new RuntimeException("PayOS báo đơn đã tồn tại và không tìm thấy link cũ: " + ex.getMessage(), ex));
        } catch (Exception e) {
            throw new RuntimeException("Không tạo được liên kết thanh toán PayOS: " + e.getMessage(), e);
        }
    }

    @Override
    public void handlePaymentWebhookRaw(String rawBody, String headerSignature) {
        try {
            log.info("=== [WEBHOOK RAW BODY] === {}", rawBody);

            if (rawBody == null || rawBody.isBlank()) {
                log.info("[PayOS] Probe webhook rỗng -> OK");
                return;
            }

            Map<String, Object> payload = objectMapper.readValue(rawBody, new TypeReference<>() {});
            Map<String, Object> data = asMap(payload.get("data"));

            Long payosOrderCode = toLong(data.get("orderCode"));
            if (payosOrderCode == null) {
                log.warn("[PayOS] Webhook thiếu orderCode -> bỏ qua");
                return;
            }

            Optional<Payment> opt = paymentRepository.findByPayosOrderCode(payosOrderCode);
            if (opt.isEmpty()) {
                log.warn("[PayOS] Không tìm thấy Payment theo payosOrderCode={} (có thể là webhook test)", payosOrderCode);
                return;
            }

            Payment payment = opt.get();

            String code = str(data.get("code"));
            String desc = str(data.get("desc"));
            String event = str(payload.get("event"));
            String status = str(data.get("status"));
            String reference = str(data.get("reference"));

            boolean isSuccess = eq(code, "00") || eq(desc, "success");
            boolean isCancelled = eq(event, "payment.cancelled")
                    || eq(status, "CANCELLED") || eq(status, "cancelled")
                    || eq(desc, "cancelled");

            if (isSuccess) {
                payment.setStatus(PaymentStatus.SUCCESS);
                payment.setPaidAt(OffsetDateTime.now());
                if (reference != null && !reference.isBlank()) {
                    payment.setTransactionId(reference);
                }
                log.info("[PayOS] ✅ Thanh toán thành công cho orderCode={} (ref={})", payosOrderCode, reference);
            } else if (isCancelled) {
                payment.setStatus(PaymentStatus.CANCELED);
                payment.setTransactionId("USER_CANCELLED_WEBHOOK");
                log.info("[PayOS] 🚫 Người dùng HỦY thanh toán qua webhook, orderCode={}", payosOrderCode);
            } else {
                payment.setStatus(PaymentStatus.FAILED);
                log.warn("[PayOS] ❌ Thanh toán thất bại/không xác định, orderCode={}", payosOrderCode);
            }

            paymentRepository.save(payment);
            log.info("[PayOS] Cập nhật payment {} -> {}", payosOrderCode, payment.getStatus());

        } catch (Exception e) {
            log.error("[PayOS] Lỗi xử lý webhook: {}", e.getMessage(), e);
        }
    }

    @Override
    public void userCancel(Long orderId) {
        var opt = paymentRepository
                .findTopByOrder_OrderIdAndStatusOrderByIdDesc(orderId, PaymentStatus.PENDING);
        if (opt.isEmpty()) {
            log.info("[PayOS] userCancel: không còn payment PENDING cho orderId={}", orderId);
            return;
        }
        Payment p = opt.get();
        p.setStatus(PaymentStatus.CANCELED);
        p.setTransactionId("USER_CANCELLED_FE");
        paymentRepository.save(p);
        log.info("[PayOS] userCancel: cập nhật orderId={} -> CANCELED", orderId);
    }

    // ===== Helpers =====
    @SuppressWarnings("unchecked")
    private static Map<String, Object> asMap(Object o) {
        return (o instanceof Map<?, ?> m) ? (Map<String, Object>) m : Map.of();
    }
    private static String str(Object o) { return (o == null ? null : String.valueOf(o)); }
    private static boolean eq(String a, String b) { return a != null && a.equalsIgnoreCase(b); }
    private static Long toLong(Object v) {
        try {
            if (v == null) return null;
            if (v instanceof Number n) return n.longValue();
            return Long.parseLong(String.valueOf(v));
        } catch (Exception ignore) { return null; }
    }
    private static int safeInt(long v, String field) {
        if (v > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(field + " vượt quá giới hạn Integer: " + v);
        }
        return (int) v;
    }
}
