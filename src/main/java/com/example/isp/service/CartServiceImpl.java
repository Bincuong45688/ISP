package com.example.isp.service;

import com.example.isp.dto.request.AddToCartRequest;
import com.example.isp.dto.response.*;
import com.example.isp.model.*;
import com.example.isp.model.enums.CartStatus;
import com.example.isp.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ZERO;

@Service
@RequiredArgsConstructor
@Transactional
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final VoucherRepository voucherRepository;

    // === Lấy hoặc tạo giỏ hàng đang mở ===
    private Cart getOpenCart(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer not found: " + customerId));

        return cartRepository.findByCustomerAndCartStatus(customer, CartStatus.OPEN)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder()
                                .customer(customer)
                                .cartStatus(CartStatus.OPEN)
                                .build()
                ));
    }

    // === Lấy giỏ hàng hiện tại ===
    @Override
    public CartResponse getCart(Long customerId) {
        Cart cart = getOpenCart(customerId);
        List<CartItem> items = cartItemRepository.findByCart(cart);
        return toCartResponse(cart, items);
    }

    // === Thêm sản phẩm vào giỏ ===
    @Override
    public CartResponse addItem(Long customerId, AddToCartRequest req) {
        Cart cart = getOpenCart(customerId);
        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + req.getProductId()));

        CartItem item = cartItemRepository.findByCart(cart).stream()
                .filter(i -> i.getProduct().getProductId().equals(product.getProductId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setCart(cart);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    newItem.setSelected(true);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + req.getQuantity());
        item.setSelected(true);
        cartItemRepository.save(item);

        return toCartResponse(cart, cartItemRepository.findByCart(cart));
    }

    // === Xóa 1 sản phẩm khỏi giỏ ===
    @Override
    public void removeItem(Long customerId, Long productId) {
        Cart cart = getOpenCart(customerId);
        cartItemRepository.findByCart(cart).stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .findFirst()
                .ifPresent(cartItemRepository::delete);
    }
    // === Giảm số lượng 1 sản phẩm trong giỏ ===
    @Override
    public CartResponse decreaseItem(Long customerId, Long productId, int quantity) {
        int dec = (quantity <= 0) ? 1 : quantity;

        Cart cart = getOpenCart(customerId);
        List<CartItem> items = cartItemRepository.findByCart(cart);

        CartItem item = items.stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Item not found in cart: " + productId));

        int newQty = item.getQuantity() - dec;
        if (newQty > 0) {
            item.setQuantity(newQty);
            // vẫn giữ chọn để FE cập nhật ngay
            item.setSelected(Boolean.TRUE);
            cartItemRepository.save(item);
        } else {
            // giảm về 0 hoặc âm -> xóa hẳn item
            cartItemRepository.delete(item);
        }

        List<CartItem> updated = cartItemRepository.findByCart(cart);
        return toCartResponse(cart, updated);
    }
    @Override
    public CartResponse increaseItem(Long customerId, Long productId, int quantity) {
        int inc = (quantity <= 0) ? 1 : quantity;

        Cart cart = getOpenCart(customerId);
        List<CartItem> items = cartItemRepository.findByCart(cart);

        CartItem item = items.stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .findFirst()
                .orElse(null);

        if (item == null) {
            // chưa có thì tạo mới với qty = inc
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new jakarta.persistence.EntityNotFoundException("Product not found: " + productId));
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(inc);
            item.setSelected(true);
        } else {
            item.setQuantity(item.getQuantity() + inc);
            item.setSelected(true);
        }

        cartItemRepository.save(item);
        List<CartItem> updated = cartItemRepository.findByCart(cart);
        return toCartResponse(cart, updated);
    }

    // === Xóa toàn bộ giỏ ===
    @Override
    public void clearCart(Long customerId) {
        Cart cart = getOpenCart(customerId);
        cartItemRepository.deleteAll(cartItemRepository.findByCart(cart));
    }

    // === Apply voucher vào giỏ hàng ===
    @Override
    public CartResponse applyVoucher(Long customerId, String voucherCode) {
        Cart cart = getOpenCart(customerId);
        List<CartItem> items = cartItemRepository.findByCart(cart);

        // Tìm voucher theo code
        Voucher voucher = voucherRepository.findByCode(voucherCode.toUpperCase())
                .orElseThrow(() -> new RuntimeException("Voucher không tồn tại: " + voucherCode));

        // Kiểm tra voucher còn valid không
        if (!voucher.isValid()) {
            String reason = !voucher.getIsActive() ? "Voucher đã bị vô hiệu hóa" :
                    java.time.LocalDateTime.now().isBefore(voucher.getStartDate()) ? "Voucher chưa có hiệu lực" :
                            java.time.LocalDateTime.now().isAfter(voucher.getEndDate()) ? "Voucher đã hết hạn" :
                                    "Voucher đã hết lượt sử dụng";
            throw new RuntimeException(reason);
        }

        // Tính subTotal
        BigDecimal subTotal = items.stream()
                .map(i -> i.getProduct().getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(ZERO, BigDecimal::add);

        // Kiểm tra minimum order amount
        if (!voucher.canBeUsedForAmount(subTotal)) {
            throw new RuntimeException(
                    String.format("Đơn hàng tối thiểu phải đạt %s VND để sử dụng voucher này",
                            voucher.getMinOrderAmount())
            );
        }

        // Tính discount amount
        BigDecimal discountAmount = voucher.calculateDiscount(subTotal);

        // Lưu voucher và discount vào cart
        cart.setVoucher(voucher);
        cart.setDiscountAmount(discountAmount);
        cartRepository.save(cart);

        return toCartResponse(cart, items);
    }

    // === Remove voucher khỏi giỏ hàng ===
    @Override
    public CartResponse removeVoucher(Long customerId) {
        Cart cart = getOpenCart(customerId);
        List<CartItem> items = cartItemRepository.findByCart(cart);

        // Xóa voucher và discount
        cart.setVoucher(null);
        cart.setDiscountAmount(null);
        cartRepository.save(cart);

        return toCartResponse(cart, items);
    }

    // === Mapper trả về dữ liệu đầy đủ cho FE ===
    private CartResponse toCartResponse(Cart cart, List<CartItem> items) {
        List<CartItemResponse> itemResponses = items.stream().map(i -> {
            BigDecimal price = i.getProduct().getPrice(); // BigDecimal
            BigDecimal lineTotal = price.multiply(BigDecimal.valueOf(i.getQuantity()));

            return CartItemResponse.builder()
                    .cartItemId(i.getCartItemId())
                    .productId(i.getProduct().getProductId())
                    .productName(i.getProduct().getProductName())
                    .productImage(i.getProduct().getProductImage())
                    .unitPrice(price)
                    .quantity(i.getQuantity())
                    .lineTotal(lineTotal)
                    .selected(Boolean.TRUE.equals(i.getSelected()))
                    .build();
        }).toList();

        BigDecimal subTotal = itemResponses.stream()
                .map(CartItemResponse::getLineTotal)
                .reduce(ZERO, BigDecimal::add);

        // Tính discount và final amount
        BigDecimal discountAmount = cart.getDiscountAmount() != null ? cart.getDiscountAmount() : ZERO;
        BigDecimal finalAmount = subTotal.subtract(discountAmount);

        return CartResponse.builder()
                .cartId(cart.getCartId())
                .cartStatus(cart.getCartStatus().name())
                .customerId(cart.getCustomer().getCustomerId())
                .customerName(cart.getCustomer().getCustomerName())
                .items(itemResponses)
                .totalItems(itemResponses.size())
                .subTotal(subTotal)
                .voucherCode(cart.getVoucher() != null ? cart.getVoucher().getCode() : null)
                .discountAmount(discountAmount)
                .finalAmount(finalAmount)
                .currency("VND")
                .build();
    }
}
