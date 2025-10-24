package com.example.isp.service;

import com.example.isp.dto.response.OrderDetailResponse;
import com.example.isp.dto.response.OrderResponse;
import com.example.isp.model.enums.Role;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrdersOfCurrentCustomer();
    OrderDetailResponse getOrderDetail(Long orderId);
    void cancleOrder(Long orderId, String username, Role role);
    void cancleOrderOfCurrentCustomer(Long orderId);
}
