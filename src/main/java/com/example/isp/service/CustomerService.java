package com.example.isp.service;

import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.RegisterCustomerRequest;
import com.example.isp.dto.request.UpdateProfileRequest;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.CustomerResponse;

public interface CustomerService {
    CustomerResponse createCustomer(RegisterCustomerRequest request);
    AuthResponse login(LoginRequest request);
    CustomerResponse getProfileByUsername(String username);
    CustomerResponse updateProfile(String username, UpdateProfileRequest request);
}
