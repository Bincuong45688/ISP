package com.example.isp.service;

import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.UpdateShipperProfileRequest;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.OrderResponse;
import com.example.isp.dto.response.ShipperProfileResponse;
import com.example.isp.dto.response.ShipperResponse;
import com.example.isp.mapper.OrderMapper;
import com.example.isp.mapper.ShipperMapper;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.Objects;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final AccountRepository accountRepository;
    private final ShipperRepository shipperRepository;
    private final ShipperMapper shipperMapper;
    private final JwtService jwtSerivce;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CloudinaryService cloudinaryService;
    private static final Logger log = LoggerFactory.getLogger(ShipperService.class);

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // Login
    public AuthResponse login(LoginRequest req) {
        Account acc = accountRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(req.getPassword(), acc.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if (acc.getRole() != Role.SHIPPER) {
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

    // Update profile
    @Transactional
    public ShipperResponse updateProfile(String username, UpdateShipperProfileRequest req) {
        Shipper shipper = shipperRepository.findByAccountUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Shipper not found"));

        Account acc = shipper.getAccount();

        if (req.getShipperName() != null) shipper.setShipperName(req.getShipperName());
        if (req.getGender() != null) shipper.setGender(req.getGender());
        if (req.getEmail() != null) acc.setEmail(req.getEmail());
        if (req.getPhone() != null) acc.setPhone(req.getPhone());

        return shipperMapper.toResponse(shipper);
    }

    // Shipper chấp nhận đơn (CONFIRMED -> SHIPPING)

    @Transactional
    public void acceptOrder(Long orderId) {
        String username = getCurrentUsername();

        // Lấy account hiện tại của shipper
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        if (account.getRole() != Role.SHIPPER) {
            throw new SecurityException("You are not authorized");
        }

        // Tìm order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Đảm bảo đã gán shipper
        if (order.getShipper() == null) {
            throw new IllegalStateException("Order has no assigned shipper");
        }
        // Chuyển trạng thái
        if (order.getStatus() != OrderStatus.CONFIRMED) {
            throw new IllegalStateException("Order is not in CONFIRMED state");
        }

        order.setStatus(OrderStatus.SHIPPING);
        orderRepository.save(order);
    }

    // ===== COMPLETE (khôi phục + RETURN SỚM) =====
    @Transactional
    public void completeOrder(Long orderId, String username, MultipartFile proofImage) {
        if (proofImage == null || proofImage.isEmpty()) {
            throw new RuntimeException("Proof image is required");
        }

        // Lấy đơn
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Nạp account hiện tại từ principal (có thể là username hoặc email)
        Account current = accountRepository.findByUsername(username)
                .orElseGet(() -> accountRepository.findByEmail(username)
                        .orElseThrow(() -> new RuntimeException("Current account not found")));

        // --- QUY TẮC NHẬN ĐƠN NHẸ NHÀNG ---
        // 1) Nếu chưa gán shipper: cho phép gán về shipper hiện tại (auto-claim)
        if (order.getShipper() == null) {
            order.setShipper(current);
            // nếu vẫn chưa vào SHIPPING, đẩy sang SHIPPING luôn để hoàn tất
            if (order.getStatus() == OrderStatus.CONFIRMED) {
                order.setStatus(OrderStatus.SHIPPING);
            }
        }
        else if (!order.getShipper().getAccountId().equals(current.getAccountId())) {
            // - nếu đơn còn ở CONFIRMED: cho phép chuyển quyền (claim) cho shipper hiện tại
            if (order.getStatus() == OrderStatus.CONFIRMED) {
                order.setShipper(current);
                order.setStatus(OrderStatus.SHIPPING);
            }
        }

        // 3) Chỉ cho phép hoàn tất khi đang SHIPPING (sau các bước trên, CONFIRMED đã được đẩy sang SHIPPING)
        if (order.getStatus() != OrderStatus.SHIPPING) {
            throw new RuntimeException("Only SHIPPING orders can be completed");
        }

        // 4) Upload ảnh POD
        String url = cloudinaryService.uploadImage(proofImage, "isp/pod");

        // 5) Lưu POD + cập nhật trạng thái
        order.setProofImageUrl(url);
        order.setProofUploadedAt(java.time.LocalDateTime.now());
        order.setProofUploadedBy(current.getUsername()); // lưu username chuẩn từ DB
        order.setStatus(OrderStatus.COMPLETED);

        orderRepository.save(order);
    }


    // Đơn chờ xác nhận (đối với shipper hiện tại)
    public List<OrderResponse> getPendingOrders() {
        String username = getCurrentUsername();
        List<Order> orders = orderRepository.findByShipperAccountUsernameAndStatus(username, OrderStatus.CONFIRMED);
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    // Đơn đang giao
    public List<OrderResponse> getActiveOrders() {
        String username = getCurrentUsername();
        List<Order> orders = orderRepository.findByShipperAccountUsernameAndStatus(username, OrderStatus.SHIPPING);
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    // Đơn đã hoàn thành
    public List<OrderResponse> getCompletedOrders() {
        String username = getCurrentUsername();
        List<Order> orders = orderRepository.findByShipperAccountUsernameAndStatus(username, OrderStatus.COMPLETED);
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }
}
