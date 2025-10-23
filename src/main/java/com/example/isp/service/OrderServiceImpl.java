package com.example.isp.service;

import com.example.isp.dto.response.OrderResponse;
import com.example.isp.model.Account;
import com.example.isp.model.Customer;
import com.example.isp.model.Order;
import com.example.isp.repository.AccountRepository;
import com.example.isp.repository.CustomerRepository;
import com.example.isp.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements  OrderService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;

    @Override
    public List<OrderResponse> getOrdersOfCurrentCustomer() {
        // 1. Lấy username từ token
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Lấy thông tin Account và Customer
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Customer customer = customerRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // 3. Lấy danh sách Order
        List<Order> orders = orderRepository.findByCustomerCustomerId(customer.getCustomerId());

        // 4. Map sang dto
        return orders.stream().map(order -> OrderResponse.builder()
            .orderId(order.getOrderId())
                    .orderDate(order.getCreatedAt())
                    .totalPrice(order.getTotalAmount())
                    .address(order.getAddress())
                    .note(order.getNote())
                    .status(order.getStatus())
                    .build()
        ).toList();
    }
}
