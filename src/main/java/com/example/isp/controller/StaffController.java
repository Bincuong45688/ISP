package com.example.isp.controller;

import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.RegisterStaffRequest;
import com.example.isp.dto.request.UpdateStaffProfileRequest;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.StaffResponse;
import com.example.isp.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

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
}