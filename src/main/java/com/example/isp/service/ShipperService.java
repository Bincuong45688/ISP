package com.example.isp.service;

import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.OrderResponse;
import com.example.isp.dto.response.ShipperProfileResponse;
import com.example.isp.mapper.OrderMapper;
import com.example.isp.model.Account;
import com.example.isp.model.Order;
import com.example.isp.model.Shipper;
import com.example.isp.model.enums.OrderStatus;
import com.example.isp.model.enums.Role;
import com.example.isp.repository.AccountRepository;
import com.example.isp.repository.OrderRepository;
import com.example.isp.repository.ShipperRepository;
import com.example.isp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final AccountRepository accountRepository;
    private final ShipperRepository shipperRepository;
    private final JwtService jwtSerivce;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Login
    public AuthResponse login(LoginRequest req) {
        Account acc = accountRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if(!passwordEncoder.matches(req.getPassword(), acc.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if(acc.getRole() != Role.SHIPPER){
            throw new IllegalStateException("Account is not SHIPPER");
        }

        UserDetails principal = User.builder()
                .username(acc.getUsername())
                .password(acc.getPassword())
                .roles(acc.getRole().name())
                .build();

        String token = jwtSerivce.generateToken(principal);

        return AuthResponse.builder()
                .token(token)
                .username(acc.getUsername())
                .email(acc.getEmail())
                .role(acc.getRole().name())
                .build();
    }

    // Xem profile
    public ShipperProfileResponse getProfile(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Shipper shipper = shipperRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

        return ShipperProfileResponse.builder()
                .username(account.getUsername())
                .shipperName(shipper.getShipperName())
                .gender(shipper.getGender())
                .phone(account.getPhone())
                .email(account.getEmail())
                .status(account.getStatus())
                .build();
    }

    // Shipper chấp nhận đơn (chuyển status từ CONFIRM sang SHIPPING)
    public void acceptOrder(Long orderId) {
        String username = getCurrentUsername();

        // Lấy account hiện tại của shipper
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if(account.getRole() != Role.SHIPPER){
            throw new SecurityException("You are not authorized");
        }

        // Tìm order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Kiểm tra shipper có phải là người được gán không
        if(!order.getShipper().getAccountId().equals(account.getAccountId())) {
            throw new SecurityException("This order is not assigned to you");
        }

        // Chuyển trạng thái
        if(order.getStatus() != OrderStatus.CONFIRMED){
            throw new IllegalStateException("Order is not in CONFIRMED state");
        }

        order.setStatus(OrderStatus.SHIPPING);
        orderRepository.save(order);
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

    // Đơn chờ xác nhận
    public List<OrderResponse> getPendingOrders() {
        String username = getCurrentUsername();
        List<Order> orders = orderRepository.findByShipperUsernameAndStatus(username, OrderStatus.CONFIRMED);
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    // Đơn đang giao
    public List<OrderResponse> getActiveOrders() {
        String username = getCurrentUsername();
        List<Order> orders = orderRepository.findByShipperUsernameAndStatus(username, OrderStatus.SHIPPING);
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    // Đơn đã hoàn thành
    public List<OrderResponse> getCompletedOrders() {
        String username = getCurrentUsername();
        List<Order> orders = orderRepository.findByShipperUsernameAndStatus(username, OrderStatus.COMPLETED);
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }
}
