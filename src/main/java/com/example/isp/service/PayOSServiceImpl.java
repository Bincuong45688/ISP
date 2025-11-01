package com.example.isp.service;

import com.example.isp.model.Order;
import com.example.isp.model.Payment;
import com.example.isp.model.enums.OrderStatus;
import com.example.isp.model.enums.PaymentStatus;
import com.example.isp.repository.OrderRepository;
import com.example.isp.repository.PaymentRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.payos.PayOS;
import vn.payos.exception.PayOSException;
import vn.payos.type.PaymentData;

import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

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

    /**
     * Tạo link thanh toán PayOS
     * - payos_order_code (gửi PayOS) = Long, duy nhất, tự tăng an toàn.
     * - order_code (nội bộ) = chuỗi hiển thị/đối soát (cố định theo Order).
     */
    @Override
    @Transactional
    public Map<String, String> createPaymentLink(Long orderId) {
        // 1) Lấy đơn & đảm bảo có order_code nội bộ cố định
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng #" + orderId));
        String internalOrderCode = ensureOrderCode(order);

        // 2) Chuẩn hóa dữ liệu
        int amountForPayOS = safeInt(order.getTotalAmount().longValue(), "amount");
        String returnUrl = successUrl;
        String cancelUrlFull = cancelUrl + "?orderId=" + orderId;

        // 3) Lấy mã PayOS (global, tăng dần, synchronized để tránh race-condition)
        long payosOrderCode = nextPayosOrderCode();

        // 4) Gọi PayOS (SDK yêu cầu Long cho orderCode)
        PaymentData paymentData = PaymentData.builder()
                .orderCode(payosOrderCode)
                .amount(amountForPayOS)
                .description(shortDesc(internalOrderCode)) // <= 25 ký tự
                .returnUrl(returnUrl)
                .cancelUrl(cancelUrlFull)
                .build();

        try {
            var response = payOS.createPaymentLink(paymentData);

            // 5) Lưu Payment
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

            // 6) Trả cho FE
            Map<String, String> result = new HashMap<>();
            result.put("checkoutUrl", response.getCheckoutUrl());
            result.put("payosOrderCode", String.valueOf(payosOrderCode));
            result.put("orderCode", internalOrderCode);
            result.put("amount", order.getTotalAmount().toPlainString());
            return result;

        } catch (PayOSException ex) {
            throw new RuntimeException("Tạo liên kết PayOS thất bại: " + ex.getMessage(), ex);
        } catch (Exception e) {
            throw new RuntimeException("Không tạo được liên kết thanh toán PayOS: " + e.getMessage(), e);
        }
    }

    /**
     * Webhook từ PayOS:
     * - SUCCESS -> Payment.SUCCESS, Order.PAID
     * - CANCEL  -> Payment.CANCELED
     * - else    -> Payment.FAILED
     *
     * Ghi chú: Chưa bật verify chữ ký (headerSignature) – có thể bổ sung nếu bạn bật xác thực webhook.
     */
    @Override
    @Transactional
    public void handlePaymentWebhookRaw(String rawBody, String headerSignature) {
        try {
            if (rawBody == null || rawBody.isBlank()) {
                log.info("[PayOS] Webhook rỗng -> bỏ qua");
                return;
            }

            Map<String, Object> payload = objectMapper.readValue(rawBody, new TypeReference<>() {});
            Map<String, Object> data = asMap(payload.get("data"));

            Object orderCodeObj = data.get("orderCode");
            if (orderCodeObj == null) {
                log.warn("[PayOS] Webhook thiếu orderCode -> bỏ qua");
                return;
            }

            Long payosOrderCode = toLong(orderCodeObj);
            if (payosOrderCode == null) {
                log.warn("[PayOS] Webhook orderCode không hợp lệ: {}", orderCodeObj);
                return;
            }

            Optional<Payment> opt = paymentRepository.findByPayosOrderCode(payosOrderCode);
            if (opt.isEmpty()) {
                log.warn("[PayOS] Không tìm thấy Payment với payos_order_code={}", payosOrderCode);
                return;
            }

            Payment payment = opt.get();
            Order order = payment.getOrder();

            String code = str(data.get("code"));       // "00" nếu success
            String desc = str(data.get("desc"));       // "success" nếu success
            String event = str(payload.get("event"));  // "payment.completed" | "payment.cancelled" ...
            String status = str(data.get("status"));   // "PAID" | "CANCELLED" ...
            String reference = str(data.get("reference")); // Mã tham chiếu ngân hàng

            boolean isSuccess = eq(code, "00") || eq(desc, "success") || eq(status, "PAID")
                    || eq(event, "payment.completed");
            boolean isCancelled = eq(event, "payment.cancelled")
                    || eq(status, "CANCELLED") || eq(status, "cancelled")
                    || eq(desc, "cancelled");

            if (isSuccess) {
                payment.setStatus(PaymentStatus.SUCCESS);
                payment.setPaidAt(OffsetDateTime.now());
                if (!isBlank(reference)) payment.setTransactionId(reference);

                if (order != null && order.getStatus() != OrderStatus.PAID) {
                    order.setStatus(OrderStatus.PAID);
                    orderRepository.save(order);
                }
                log.info("[PayOS] SUCCESS -> Order {} (order_code={}) cập nhật PAID",
                        order != null ? order.getOrderId() : null,
                        order != null ? order.getOrderCode() : null);

            } else if (isCancelled) {
                payment.setStatus(PaymentStatus.CANCELED);
                if (isBlank(payment.getTransactionId())) {
                    payment.setTransactionId("USER_CANCELLED_WEBHOOK");
                }
                log.info("[PayOS] CANCELLED (order_code={})",
                        order != null ? order.getOrderCode() : "N/A");

            } else {
                payment.setStatus(PaymentStatus.FAILED);
                if (isBlank(payment.getTransactionId())) {
                    payment.setTransactionId("FAILED_WEBHOOK");
                }
                log.warn("[PayOS] FAILED/UNKNOWN (order_code={})",
                        order != null ? order.getOrderCode() : "N/A");
            }

            paymentRepository.save(payment);

        } catch (Exception e) {
            log.error("[PayOS] Lỗi xử lý webhook: {}", e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public void userCancel(Long orderId) {
        var opt = paymentRepository
                .findTopByOrder_OrderIdAndStatusOrderByIdDesc(orderId, PaymentStatus.PENDING);
        if (opt.isEmpty()) {
            log.info("[PayOS] userCancel: không còn payment PENDING cho orderId={}", orderId);
            return;
        }
        Payment p = opt.get();
        p.setStatus(PaymentStatus.CANCELED);
        if (isBlank(p.getTransactionId())) {
            p.setTransactionId("USER_CANCELLED_FE");
        }
        paymentRepository.save(p);
        log.info("[PayOS] userCancel: cập nhật orderId={} -> CANCELED", orderId);
    }

    // ===================== Helpers =====================

    /**
     * Lấy payos_order_code mới:
     * - Cố gắng +1 từ payment cuối cùng (global).
     * - Nếu null/không hợp lệ, fallback sang epoch milli + suffix ngẫu nhiên để chắc chắn duy nhất.
     * - Synchronized để tránh 2 thread cấp trùng trong cùng instance.
     */
    private static final Object CODE_LOCK = new Object();

    private long nextPayosOrderCode() {
        synchronized (CODE_LOCK) {
            long base = paymentRepository.findTopByOrderByIdDesc()
                    .map(p -> p.getPayosOrderCode() == null ? 0L : p.getPayosOrderCode())
                    .orElse(0L);

            long candidate = (base > 0) ? base + 1 : System.currentTimeMillis();

            // Thêm 1 chữ số ngẫu nhiên khi khởi tạo từ epoch để giảm xác suất trùng mili-giây hiếm hoi
            if (base <= 0) {
                candidate = candidate * 10 + ThreadLocalRandom.current().nextInt(0, 10);
            }
            return candidate;
        }
    }

    /** Đảm bảo order_code nội bộ cố định. */
    private String ensureOrderCode(Order order) {
        if (isBlank(order.getOrderCode())) {
            order.setOrderCode("ORD" + order.getOrderId());
            orderRepository.save(order);
        }
        return order.getOrderCode();
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> asMap(Object o) {
        return (o instanceof Map<?, ?> m) ? (Map<String, Object>) m : Map.of();
    }

    private static String str(Object o) {
        return o == null ? null : String.valueOf(o);
    }

    private static boolean isBlank(String s) {
        return s == null || s.isBlank();
    }

    private static boolean eq(String a, String b) {
        return a != null && b != null && a.equalsIgnoreCase(b);
    }

    private static int safeInt(long v, String field) {
        if (v > Integer.MAX_VALUE)
            throw new IllegalArgumentException(field + " vượt giới hạn Integer: " + v);
        if (v < 0)
            throw new IllegalArgumentException(field + " âm không hợp lệ: " + v);
        return (int) v;
    }

    private static String shortDesc(String internalOrderCode) {
        String s = "PAY " + internalOrderCode; // ví dụ: "PAY ORD123"
        return s.length() <= 25 ? s : s.substring(0, 25);
    }

    private static Long toLong(Object o) {
        try {
            if (o instanceof Number n) return n.longValue();
            return Long.parseLong(String.valueOf(o));
        } catch (Exception e) {
            return null;
        }
    }
}