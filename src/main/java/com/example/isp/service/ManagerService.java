package com.example.isp.service;

import com.example.isp.dto.request.CreateShipperRequest;
import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.CreateStaffRequest;
import com.example.isp.dto.request.UpdateManagerProfileRequest;
import com.example.isp.dto.response.*;

import java.util.List;

public interface ManagerService {
    AuthResponse register(CreateStaffRequest req);
    AuthResponse login(LoginRequest req);
    ManagerResponse getProfile(String username);
    ManagerResponse updateProfile(String username, UpdateManagerProfileRequest req);
    void createShipper(CreateShipperRequest req);
    List<ShipperResponse> getAllShippers();

    void createStaff(CreateStaffRequest req);       // 🔥 mới thêm
    List<StaffResponse> getAllStaff();

    List<AllCustomerResponse> getAllCustomer();
}
