package com.example.isp.service;

import com.example.isp.dto.request.CheckoutRequest;
import com.example.isp.dto.response.CheckoutResponse;

public interface CheckoutService {
    CheckoutResponse checkout(CheckoutRequest request);
}
