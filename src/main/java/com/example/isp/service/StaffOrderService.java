package com.example.isp.service;

import com.example.isp.dto.response.OrderResponse;
import com.example.isp.mapper.OrderMapper;
import com.example.isp.model.Account;
import com.example.isp.model.Order;
import com.example.isp.model.Shipper;
import com.example.isp.model.enums.OrderStatus;
import com.example.isp.model.enums.Role;
import com.example.isp.repository.OrderRepository;
import com.example.isp.repository.ShipperRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffOrderService {

    private final OrderRepository orderRepository;
    private final ShipperRepository shipperRepository;
    private final OrderMapper orderMapper;

    // Xem tất cả đơn hàng trong hệ thống
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAllWithVoucher()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    // Staff xác nhận đơn hàng từ khách, chuyển từ PENDING → CONFIRMED.
    public void confirmOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getStatus() != OrderStatus.PAID) {
            throw new RuntimeException("Only pending orders can be confirmed");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
    }

    // Gán một shipper cụ thể cho đơn hàng và chuyển trạng thái sang SHIPPING.
    public Order assignShipper(Long orderId, Long shipperId) {
        // 1. Tìm Order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // 2. Tìm Shipper theo ID
        Shipper shipper = shipperRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

        // 3. Lấy account tương ứng với shipper
        Account shipperAccount = shipper.getAccount();

        if (shipperAccount == null || shipperAccount.getRole() != Role.SHIPPER) {
            throw new RuntimeException("Account is not a shipper");
        }

        // 4. Gán vào order
        order.setShipper(shipper);
        return orderRepository.save(order);
    }



}
