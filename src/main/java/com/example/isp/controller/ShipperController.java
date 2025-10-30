package com.example.isp.controller;

import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.UpdateShipperProfileRequest;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.ShipperProfileResponse;
import com.example.isp.dto.response.ShipperResponse;
import com.example.isp.service.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shipper")
@RequiredArgsConstructor
public class ShipperController {

    private final ShipperService shipperService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest req) {
        return ResponseEntity.ok(shipperService.login(req));
    }

    @GetMapping("/profile")
    public ResponseEntity<ShipperProfileResponse> getProfile(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(shipperService.getProfile(user.getUsername()));
    }

    @PutMapping("/profile")
    public ResponseEntity<ShipperResponse> updateProfile(
            @AuthenticationPrincipal User user,
            @RequestBody UpdateShipperProfileRequest req){
        return ResponseEntity.ok(shipperService.updateProfile(user.getUsername(), req));
    }
}
