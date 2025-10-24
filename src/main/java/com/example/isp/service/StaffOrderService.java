package com.example.isp.service;

import com.example.isp.dto.response.OrderResponse;
import com.example.isp.mapper.OrderMapper;
import com.example.isp.model.Account;
import com.example.isp.model.Order;
import com.example.isp.model.enums.OrderStatus;
import com.example.isp.model.enums.Role;
import com.example.isp.repository.AccountRepository;
import com.example.isp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffOrderService {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final OrderMapper orderMapper;

    // Xem tất cả đơn hàng trong hệ thống
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    // Staff xác nhận đơn hàng từ khách, chuyển từ PENDING → CONFIRMED.
    public void confirmOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Only pending orders can be confirmed");
        }

        order.setStatus(OrderStatus.CONFIRMED);
        orderRepository.save(order);
    }

    // Gán một shipper cụ thể cho đơn hàng và chuyển trạng thái sang SHIPPING.
    public void assignShipper(Long orderId, Long shipperId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Account shipper = accountRepository.findById(shipperId)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

        if(shipper.getRole() != Role.SHIPPER){
            throw new RuntimeException("Account is not a shipper");
        }

        order.setShipper(shipper);
        order.setStatus(OrderStatus.SHIPPING);
        orderRepository.save(order);
    }


}
