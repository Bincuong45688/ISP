package com.example.isp.controller;


import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.response.*;
import com.example.isp.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(staffService.login(req));
    }

    @GetMapping("/profile")
    public ResponseEntity<StaffProfileResponse> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(staffService.getProfile(user.getUsername()));
    }

    @PutMapping("/profile")
    public ResponseEntity<StaffResponse> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateStaffProfileRequest req){
        return ResponseEntity.ok(staffService.updateProfile(user.getUsername(), req));
    }

}
