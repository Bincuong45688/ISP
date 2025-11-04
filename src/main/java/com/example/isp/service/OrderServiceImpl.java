package com.example.isp.service;

import com.example.isp.dto.response.OrderDetailResponse;
import com.example.isp.dto.response.OrderItemResponse;
import com.example.isp.dto.response.OrderResponse;
import com.example.isp.mapper.OrderMapper;
import com.example.isp.model.*;
import com.example.isp.model.enums.OrderStatus;
import com.example.isp.model.enums.Role;
import com.example.isp.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements  OrderService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;
    private final OrderMapper orderMapper;

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

        // 4. Trả về
        return orders.stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    public OrderDetailResponse getOrderDetail(Long orderId) {
        // 1. Lấy order
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        OrderDetailResponse response = orderMapper.toOrderDetailResponse(order);

        // Nếu bạn vẫn muốn thêm danh sách sản phẩm chi tiết
        List<OrderItemResponse> items = orderDetailRepository.findByOrderOrderId(orderId)
                .stream()
                .map(od -> OrderItemResponse.builder()
                        .productId(od.getProduct().getProductId())
                        .productName(od.getProduct().getProductName())
                        .price(od.getProduct().getPrice())
                        .quantity(od.getQuantity())
                        .subtotal(od.getProduct().getPrice()
                                .multiply(new BigDecimal(od.getQuantity())))
                        .build()
                ).toList();

        response.setItems(items);
        return response;
    }

    @Override
    public void cancleOrder(Long orderId, String username, Role role){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if(order.getStatus() == OrderStatus.SHIPPING || order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel an order in progress or completed");
        }

        boolean isCustomerOwner = order.getCustomer().getAccount().getUsername().equals(username);

        if(role == Role.STAFF || (role == Role.CUSTOMER && isCustomerOwner)) {

            restoreStockAfterCancel(order);

            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        } else {
            throw new SecurityException("You are not authorized to cancel this order");
        }
    }

    @Override
    public void cancleOrderOfCurrentCustomer(Long orderId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Kiểm tra own
        if(!order.getCustomer().getAccount().getUsername().equals(username)) {
            throw new SecurityException("You can not cancel other order");
        }

        // Chỉ được hủy khi đơn hàng chưa giao
        if(order.getStatus() == OrderStatus.SHIPPING || order.getStatus() == OrderStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel an order in progress or completed");
        }

        restoreStockAfterCancel(order);
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }

    // Hoàn lại kho nếu đơn hàng bị huỷ
    @Transactional
    public void restoreStockAfterCancel(Order order) {
        List<OrderDetail> details = orderDetailRepository.findByOrder(order);

        for (OrderDetail detail : details) {
            Product product = detail.getProduct();
            int productQuantity = detail.getQuantity();

            // Tìm checklist theo product
            List<Checklist> checklists =
                    checklistRepository.findByProductDetail_Product_ProductId(product.getProductId());

            for (Checklist checklist : checklists) {
                ChecklistItem item = checklist.getItem();
                int restoreQty = checklist.getQuantity() * productQuantity;
                item.setStockQuantity(item.getStockQuantity() + restoreQty);
                checklistItemRepository.save(item);
            }
        }
    }
}
