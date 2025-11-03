package com.example.isp.controller;

import com.example.isp.dto.request.CreateShipperRequest;
import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.RegisterStaffRequest;
import com.example.isp.dto.request.UpdateStaffProfileRequest;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.CustomerResponse;
import com.example.isp.dto.response.ShipperResponse;
import com.example.isp.dto.response.StaffResponse;
import com.example.isp.service.CustomerService;
import com.example.isp.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;
    private final CustomerService customerService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody RegisterStaffRequest req) {
        return staffService.register(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return staffService.login(req);   // dùng LoginRequest + AuthResponse sẵn có
    }

    @GetMapping("/profile")
    public StaffResponse myProfile(@AuthenticationPrincipal User user) {
        return staffService.getProfile(user.getUsername());
    }

    @PutMapping("/profile")
    public StaffResponse update(@AuthenticationPrincipal User user,
                                @RequestBody UpdateStaffProfileRequest req) {
        return staffService.updateProfile(user.getUsername(), req);
    }

    @PostMapping("/shippers")
    public ResponseEntity<?> createShipper(@RequestBody CreateShipperRequest request) {
        staffService.createShipper(request);
        return  ResponseEntity.ok("Shipper account created successfully");
    }

    @GetMapping("/shippers")
    public ResponseEntity<List<ShipperResponse>> getAllShippers() {
        return ResponseEntity.ok(staffService.getAllShippers());
    }

    @GetMapping("/customer")
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(staffService.getAllCustomer());
    }
}