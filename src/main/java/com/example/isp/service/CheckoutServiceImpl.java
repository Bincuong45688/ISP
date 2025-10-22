package com.example.isp.service;

import com.example.isp.dto.request.CheckoutRequest;
import com.example.isp.dto.response.CheckoutResponse;
import com.example.isp.model.*;
import com.example.isp.model.enums.CartStatus;
import com.example.isp.model.enums.ProductStatus;
import com.example.isp.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements  CheckoutService{

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        // 1. Lấy username từ token (người dùng đang đăng nhập)
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        // 2. Tìm Account tương ứng
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        // 3. Tìm Customer dựa trên Account
        Customer customer = customerRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // 4. Tìm giỏ hàng ACTIVE của Customer
        Cart cart = cartRepository.findByCustomerAndCartStatus(customer, CartStatus.OPEN)
                .orElseThrow(() -> new RuntimeException("No active cart found for this customer"));

        // 5. Lấy các item được chọn để checkout
        List<CartItem> selectedItems = cart.getCartItems().stream()
                .filter(CartItem::getSelected)
                .toList();
        if(selectedItems.isEmpty()){
            throw new RuntimeException("No items selected for checkout");
        }

        // 6. Kiểm tra tình trạng sản phẩm
        BigDecimal total = BigDecimal.ZERO;
        for(CartItem item : selectedItems){
            Product product = item.getProduct();

            if(product.getStatus() != ProductStatus.AVAILABLE){
                throw new RuntimeException("Product " + product.getProductName() + " is currently unavailable");
            }
            BigDecimal lineTotal = product.getPrice()
                    .multiply(BigDecimal.valueOf(item.getQuantity()));

            total = total.add(lineTotal);
        }

        // 7. Tạo Order mới
        Order order = Order.builder()
                .customer(customer)
                .address(request.getAddress() != null ? request.getAddress() : customer.getAddress())
                .phone(request.getPhone() != null ? request.getPhone() : account.getPhone())
                .paymentMethod(request.getPaymentMethod())
                .totalAmount(total)
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();
        orderRepository.save(order);

        // 8. Tạo OrderDetail cho từng CartItem đã chọn
        for(CartItem item : selectedItems){
            Product product = item.getProduct();

            BigDecimal unitPrice = product.getPrice();
            BigDecimal totalPrice = unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));


            OrderDetail detail = OrderDetail.builder()
                    .order(order)
                    .product(product)
                    .quantity(item.getQuantity())
                    .unitPrice(unitPrice)
                    .totalPrice(totalPrice)
                    .build();
            orderDetailRepository.save(detail);
        }

        // 9. Cập nhật trạng thái Cart → CHECKED_OUT
        cart.setCartStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        // Tạo cart mới cho customer sau khi checkout thành công
        Cart newCart = Cart.builder()
                .customer(customer)
                .cartStatus(CartStatus.OPEN)
                .build();
        cartRepository.save(newCart);


        // 10. Trả về response
        return CheckoutResponse.builder()
                .orderId(order.getOrderId())
                .totalAmount(total)
                .paymentMethod(order.getPaymentMethod())
                .status(order.getStatus())
                .message("Checkout successful")
                .build();
    }
}
