package com.example.isp.service;

import com.example.isp.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {
    List<OrderResponse> getOrdersOfCurrentCustomer();
}
