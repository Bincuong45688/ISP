package com.example.isp.service;

import com.example.isp.dto.request.CheckoutRequest;
import com.example.isp.dto.response.CheckoutResponse;
import com.example.isp.model.*;
import com.example.isp.model.enums.CartStatus;
import com.example.isp.model.enums.OrderStatus;
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
    private final ChecklistRepository checklistRepository;
    private final ChecklistItemRepository checklistItemRepository;

    @Override
    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        Customer customer = customerRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        Cart cart = cartRepository.findByCustomerAndCartStatus(customer, CartStatus.OPEN)
                .orElseThrow(() -> new RuntimeException("Không có giỏ hàng hoạt động"));

        List<CartItem> cartItems = cartItemRepository.findByCart(cart);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Giỏ hàng rỗng");
        }
        // STEP 1: Kiểm tra tồn kho trước khi tạo đơn hàng
        for (CartItem item : cartItems) {
            Product product = item.getProduct();

            if(product.getStatus() != ProductStatus.AVAILABLE){
                throw new RuntimeException("Sản phẩm" + product.getProductName() + "hiện không thể mua");
            }
            
            checkStockAvailable(product, item.getQuantity());
        }

        // STEP 2: Tạo đơn hàng
        Order order = new Order();
        order.setCustomer(customer);
        order.setAddress(request.getAddress());
        order.setPhone(request.getPhone());
        order.setReceiverName(request.getFullName());
        order.setReceiverEmail(request.getEmail());
        order.setNote(request.getNote());
        order.setPaymentMethod(request.getPaymentMethod()); // <— thêm dòng này
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setTotalAmount(BigDecimal.ZERO);

        order = orderRepository.save(order);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            Product product = item.getProduct();

            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(item.getQuantity());
            detail.setUnitPrice(product.getPrice());
            detail.setTotalPrice(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));

            orderDetailRepository.save(detail);

            totalAmount = totalAmount.add(detail.getTotalPrice());

            // STEP 3: Trừ tồn kho sau khi tạo đơn
            updateStockAfterOrder(product, item.getQuantity());
        }

        order.setTotalAmount(totalAmount);
        orderRepository.save(order);

        // Cập nhật trạng thái giỏ hàng
        cart.setCartStatus(CartStatus.CHECKED_OUT);
        cartRepository.save(cart);

        // Sau khi checkout xong, tạo giỏ hàng mới OPEN cho khách
        Cart newCart = new Cart();
        newCart.setCustomer(customer);
        newCart.setCartStatus(CartStatus.OPEN);
        cartRepository.save(newCart);

        return CheckoutResponse.builder()
                .orderId(order.getOrderId())
                .receiverName(order.getReceiverName())
                .email(order.getReceiverEmail())
                .phone(order.getPhone())
                .address(order.getAddress())
                .paymentMethod(order.getPaymentMethod())
                .totalAmount(totalAmount)
                .status(order.getStatus().name())
                .createdAt(order.getCreatedAt())
                .message("Đặt hàng thành công")
                .build();
    }

    // Kiểm tra tồn kho cho từng product
    @Override
    public void checkStockAvailable(Product product, int productQuantity) {
        List<Checklist> checklists = checklistRepository
                .findByProductDetail_Product_ProductId(product.getProductId());

        for(Checklist checklist: checklists){
            ChecklistItem item = checklist.getItem();
            int required = checklist.getQuantity() * productQuantity;

            if (item.getStockQuantity() < required) {
                throw new RuntimeException("Không đủ hàng trong kho cho: " + item.getItemName()
                        + " (cần " + required + " " + item.getUnit() + ", còn " + item.getStockQuantity() + ")");
            }
        }
    }

    // Update lại kho sau khi đặt thành công
    @Override
    public void updateStockAfterOrder(Product product, int productQuantity) {
        List<Checklist> checklists = checklistRepository
                .findByProductDetail_Product_ProductId(product.getProductId());

        for(Checklist checklist: checklists){
            ChecklistItem item = checklist.getItem();
            int required = checklist.getQuantity() * productQuantity;
            item.setStockQuantity(item.getStockQuantity() - required);
            checklistItemRepository.save(item);
        }
    }

}
