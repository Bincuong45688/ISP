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
     * - payos_order_code (gửi PayOS) = số Long, tự tăng +1 mỗi lần.
     * - order_code (nội bộ) = chuỗi đẹp để hiển thị/đối soát.
     */
    @Override
    public Map<String, String> createPaymentLink(Long orderId) {
        // 1) Lấy đơn & đảm bảo có order_code nội bộ cố định
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy đơn hàng #" + orderId));
        String internalOrderCode = ensureOrderCode(order); // ví dụ: "NNV-20251031-U6J6Y"

        // 2) Chuẩn hóa dữ liệu
        int amountForPayOS = safeInt(order.getTotalAmount().longValue(), "amount");
        String returnUrl = successUrl;
        String cancelUrlFull = cancelUrl + "?orderId=" + orderId;

        // 3) Lấy mã PayOS tự tăng (global)
        long payosOrderCode = getNextPayosOrderCode();

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

            // 5) Lưu Payment (lưu payos_order_code dạng chuỗi cho dễ đọc)
            Payment payment = Payment.builder()
                    .order(order)
                    .provider("PAYOS")
                    .status(PaymentStatus.PENDING)
                    .amount(order.getTotalAmount())
                    .payosOrderCode(String.valueOf(payosOrderCode))
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

            // PayOS có thể trả số hoặc chuỗi -> convert an toàn sang String
            Object orderCodeObj = data.get("orderCode");
            String payosOrderCode = (orderCodeObj == null) ? null : String.valueOf(orderCodeObj);
            if (isBlank(payosOrderCode)) {
                log.warn("[PayOS] Webhook thiếu orderCode -> bỏ qua");
                return;
            }

            Optional<Payment> opt = paymentRepository.findByPayosOrderCode(payosOrderCode);
            if (opt.isEmpty()) {
                log.warn("[PayOS] Không tìm thấy Payment với payos_order_code={}", payosOrderCode);
                return;
            }

            Payment payment = opt.get();
            Order order = payment.getOrder();

            String code = str(data.get("code"));
            String desc = str(data.get("desc"));
            String event = str(payload.get("event"));
            String status = str(data.get("status"));
            String reference = str(data.get("reference"));

            boolean isSuccess = eq(code, "00") || eq(desc, "success") || eq(status, "PAID");
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
                log.info("[PayOS] Thanh toán thành công -> Order {} (order_code={}) cập nhật PAID",
                        order != null ? order.getOrderId() : null,
                        order != null ? order.getOrderCode() : null);

            } else if (isCancelled) {
                payment.setStatus(PaymentStatus.CANCELED);
                payment.setTransactionId("USER_CANCELLED_WEBHOOK");
                log.info("[PayOS] Người dùng hủy thanh toán (order_code={})",
                        order != null ? order.getOrderCode() : "N/A");

            } else {
                payment.setStatus(PaymentStatus.FAILED);
                log.warn("[PayOS] Thanh toán thất bại/không xác định (order_code={})",
                        order != null ? order.getOrderCode() : "N/A");
            }

            paymentRepository.save(payment);

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

    // ===================== Helpers =====================

    /** Lấy payos_order_code mới: lấy payment id mới nhất và +1 (global auto-increment). */
    private long getNextPayosOrderCode() {
        return paymentRepository.findTopByOrderByIdDesc()
                .map(p -> {
                    try {
                        return Long.parseLong(p.getPayosOrderCode()) + 1;
                    } catch (Exception e) {
                        return 1L;
                    }
                })
                .orElse(1L);
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
        return a != null && a.equalsIgnoreCase(b);
    }

    private static int safeInt(long v, String field) {
        if (v > Integer.MAX_VALUE)
            throw new IllegalArgumentException(field + " vượt giới hạn Integer: " + v);
        return (int) v;
    }

    /** Mô tả ngắn gọn để không vượt 25 ký tự (yêu cầu PayOS). */
    private static String shortDesc(String internalOrderCode) {
        String s = "PAY " + internalOrderCode;
        return s.length() <= 25 ? s : s.substring(0, 25);
    }
}
