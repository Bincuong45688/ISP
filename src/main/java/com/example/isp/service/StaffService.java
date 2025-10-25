package com.example.isp.service;

import com.example.isp.dto.request.CreateShipperRequest;
import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.RegisterStaffRequest;
import com.example.isp.dto.request.UpdateStaffProfileRequest;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.StaffResponse;

public interface StaffService {
    AuthResponse register(RegisterStaffRequest req);
    AuthResponse login(LoginRequest req);
    StaffResponse getProfile(String username);
    StaffResponse updateProfile(String username, UpdateStaffProfileRequest req);
    void createShipper(CreateShipperRequest req);
}
