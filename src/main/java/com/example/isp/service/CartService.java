package com.example.isp.service;

import com.example.isp.dto.request.AddToCartRequest;
import com.example.isp.dto.response.CartResponse;

public interface CartService {
    CartResponse getCart(Long customerId);
    CartResponse addItem(Long customerId, AddToCartRequest req);
    void removeItem(Long customerId, Long productId);
    void clearCart(Long customerId);
    CartResponse decreaseItem(Long customerId, Long productId, int quantity);
    CartResponse increaseItem(Long customerId, Long productId, int quantity);

    // Voucher methods
    CartResponse applyVoucher(Long customerId, String voucherCode);
    CartResponse removeVoucher(Long customerId);

}
