package com.example.isp.controller;

import com.example.isp.dto.request.CreateShipperRequest;
import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.CreateStaffRequest;
import com.example.isp.dto.request.UpdateManagerProfileRequest;
import com.example.isp.dto.response.*;
import com.example.isp.service.CustomerService;
import com.example.isp.service.ManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerController {

    private final ManagerService managerService;

    @PostMapping("/register")
    public AuthResponse register(@RequestBody CreateStaffRequest req) {
        return managerService.register(req);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest req) {
        return managerService.login(req);   // dùng LoginRequest + AuthResponse sẵn có
    }

    @GetMapping("/profile")
    public ManagerResponse myProfile(@AuthenticationPrincipal User user) {
        return managerService.getProfile(user.getUsername());
    }

    @PutMapping("/profile")
    public ManagerResponse update(@AuthenticationPrincipal User user,
                                  @RequestBody UpdateManagerProfileRequest req) {
        return managerService.updateProfile(user.getUsername(), req);
    }

    @PostMapping("/shippers")
    public ResponseEntity<?> createShipper(@RequestBody CreateShipperRequest request) {
        managerService.createShipper(request);
        return  ResponseEntity.ok("Shipper account created successfully");
    }

    @PostMapping("/staff")
    public ResponseEntity<?> createStaff(@RequestBody CreateStaffRequest request) {
        managerService.createStaff(request);
        return ResponseEntity.ok("Staff account created successfully");
    }

    @GetMapping("/shippers")
    public ResponseEntity<List<ShipperResponse>> getAllShippers() {
        return ResponseEntity.ok(managerService.getAllShippers());
    }

    @GetMapping("/customer")
    public ResponseEntity<List<AllCustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(managerService.getAllCustomer());
    }

    @GetMapping("/staff")
    public ResponseEntity<List<StaffResponse>> getAllStaff() {
        return ResponseEntity.ok(managerService.getAllStaff());
    }

}