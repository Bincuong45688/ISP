package com.example.isp.service;

import com.example.isp.dto.response.OrderResponse;
import com.example.isp.mapper.OrderMapper;
import com.example.isp.model.Order;
import com.example.isp.model.enums.OrderStatus;
import com.example.isp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    // Lấy danh sách tất cả các đơn được giao cho shipper hiện tại
    public List<OrderResponse> getOrdersByShipper(String username) {
        // Lấy tất cả đơn có shipper.username = username
        List<Order> orders = orderRepository.findByShipperUsername(username);

        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    // Shipper đánh dấu đơn đã giao thành công
    public void completeOrder(Long orderId, String username) {

        // 1. Lấy đơn hàng theo orderId
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getShipper() == null || !order.getShipper().getUsername().equals(username)) {
            throw new RuntimeException("You are not assigned to this order");
        }

        if(order.getStatus() != OrderStatus.SHIPPING) {
            throw new RuntimeException("Only shipping orders can be completed");
        }

        order.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(order);
    }
}
