package com.example.isp.service;

import com.example.isp.model.Order;
import com.example.isp.model.Payment;
import com.example.isp.model.WebhookType;
import com.example.isp.model.enums.OrderStatus;
import com.example.isp.model.enums.PaymentStatus;
import com.example.isp.repository.OrderRepository;
import com.example.isp.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;

import java.math.BigDecimal;
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

    // TẠO LINK THANH TOÁN
    @Override
    @Transactional
    public Map<String, String> createPaymentLink(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Đảm bảo orderCode không bị null
        ensureOrderCode(order);

        long amount = order.getTotalAmount() != null
                ? order.getTotalAmount().longValue()
                : 0L;

        if (amount <= 0) {
            throw new RuntimeException("Tổng tiền không hợp lệ.");
        }

        long payosOrderCode = generateUniqueOrderCode(orderId);

        String desc = "PAY " + order.getOrderCode();
        if (desc.length() > 25) desc = desc.substring(0, 25);

        CreatePaymentLinkRequest req = CreatePaymentLinkRequest.builder()
                .orderCode(payosOrderCode)
                .amount(amount)
                .description(desc)
                .returnUrl(successUrl)
                .cancelUrl(cancelUrl)
                .build();

        try {
            CreatePaymentLinkResponse res = payOS.paymentRequests().create(req);

            Payment payment = Payment.builder()
                    .order(order)
                    .provider("PAYOS")
                    .payosOrderCode(payosOrderCode)
                    .amount(BigDecimal.valueOf(amount))
                    .status(PaymentStatus.PENDING)
                    .checkoutUrl(res.getCheckoutUrl())
                    .qrCode(res.getQrCode())
                    .paymentLinkId(res.getPaymentLinkId())
                    .createdAt(OffsetDateTime.now())
                    .build();

            paymentRepository.saveAndFlush(payment);

            Map<String, String> result = new HashMap<>();
            result.put("checkoutUrl", res.getCheckoutUrl());
            result.put("qrCode", res.getQrCode());
            result.put("paymentLinkId", res.getPaymentLinkId());
            result.put("orderCode", String.valueOf(payosOrderCode));

            return result;

        } catch (Exception e) {
            log.error("PayOS Create Link Error: {}", e.getMessage());
            throw new RuntimeException("Lỗi tạo liên kết thanh toán PayOS");
        }
    }

    private long generateUniqueOrderCode(Long orderId) {

        // Giới hạn orderId còn 7 digits để không vượt giới hạn
        long prefix = orderId % 10_000_000;

        // 2 số cuối của milliseconds (0–99)
        long timePart = System.currentTimeMillis() % 100;

        // Random 2 digits (10–99)
        int randomPart = ThreadLocalRandom.current().nextInt(10, 99);

        // Ghép lại thành 1 số duy nhất
        long orderCode = prefix * 10_000 + timePart * 100 + randomPart;

        return orderCode;
    }

    // =================== Helper ===================
    private long generateOrderCode() {
        return System.currentTimeMillis() + ThreadLocalRandom.current().nextInt(100, 999);
    }

    private void ensureOrderCode(Order order) {
        if (order.getOrderCode() == null || order.getOrderCode().isBlank()) {
            order.setOrderCode("ORD-" + generateOrderCode());
            orderRepository.save(order);
        }
    }
    @Override
    @Transactional
    public void userCancel(Long orderId) {

        // Lấy Payment đang PENDING của Order
        Optional<Payment> opt = paymentRepository
                .findTopByOrderOrderIdAndStatusOrderByIdDesc(orderId, PaymentStatus.PENDING);

        if (opt.isEmpty()) {
            log.info("[PayOS] userCancel: Không còn payment PENDING cho order {}", orderId);
        } else {
            Payment payment = opt.get();

            // Cập nhật Payment → CANCELED
            payment.setStatus(PaymentStatus.CANCELED);

            if (payment.getTransactionId() == null || payment.getTransactionId().isBlank()) {
                payment.setTransactionId("USER_CANCEL");
            }

            paymentRepository.save(payment);
        }

        // Cập nhật Order → CANCELLED
        orderRepository.findById(orderId).ifPresent(order -> {

            // Không cho hủy nếu đã: PAID, SHIPPING, COMPLETED
            if (order.getStatus() == OrderStatus.PAID ||
                    order.getStatus() == OrderStatus.SHIPPING ||
                    order.getStatus() == OrderStatus.COMPLETED) {

                log.warn("[PayOS] userCancel: Order {} không thể hủy vì trạng thái={}",
                        orderId, order.getStatus());
                return;
            }

            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);

            log.info("[PayOS] userCancel: Đã cập nhật Order {} -> CANCELLED", orderId);
        });
    }
    @Override
    @Transactional
    public void handlePaymentWebhookRaw(String rawBody, String signature) {
        log.info("== HANDLE WEBHOOK RAW ==");

        try {
            if (rawBody == null || rawBody.isEmpty()) {
                log.error("Webhook empty body");
                return;
            }

            WebhookType webhook = objectMapper.readValue(rawBody, WebhookType.class);

            if (webhook == null || webhook.getData() == null) {
                log.error("Webhook missing data");
                return;
            }

            long orderCode = webhook.getData().getOrderCode();
            String code = webhook.getData().getCode();
            String txId = webhook.getData().getTransactionId();

            Optional<Payment> opt = paymentRepository.findByPayosOrderCode(orderCode);

            if (opt.isEmpty()) {
                log.error("Payment not found for {}", orderCode);
                return;
            }

            Payment payment = opt.get();

            if (payment.getStatus() != PaymentStatus.PENDING) {
                log.warn("Webhook ignored (already processed)");
                return;
            }

            // SUCCESS
            if ("00".equalsIgnoreCase(code)) {

                payment.setStatus(PaymentStatus.SUCCESS);
                payment.setPaidAt(OffsetDateTime.now());
                payment.setTransactionId(txId);

                paymentRepository.saveAndFlush(payment);

                Order order = payment.getOrder();
                order.setStatus(OrderStatus.PAID);

                orderRepository.saveAndFlush(order);

                log.info("Payment SUCCESS for {}", orderCode);

            } else {
                // FAIL
                payment.setStatus(PaymentStatus.FAILED);
                payment.setTransactionId(txId);

                paymentRepository.saveAndFlush(payment);

                log.warn("Payment FAILED for {}", orderCode);
            }

        } catch (Exception ex) {
            log.error("Webhook processing failed: {}", ex.getMessage(), ex);
        }
    }


}
