package com.example.isp.service;

import com.example.isp.dto.request.CheckoutRequest;
import com.example.isp.dto.response.CheckoutResponse;
import com.example.isp.model.Order;
import com.example.isp.model.Product;

public interface CheckoutService {
    CheckoutResponse checkout(CheckoutRequest request);

    // Kiểm tra tồn kho cho từng product
    void checkStockAvailable(Product product, int productQuantity);

    // Update lại kho sau khi đặt thành công
    void updateStockAfterOrder(Product product, int productQuantity);

}
