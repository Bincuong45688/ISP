package com.example.isp.controller;

import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.RegisterCustomerRequest;
import com.example.isp.dto.request.UpdateCustomerRequest;
import com.example.isp.dto.request.VerifyEmailRequest;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.CustomerResponse;
import com.example.isp.dto.response.VerifyEmailResponse;
import com.example.isp.model.Customer;
import com.example.isp.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> register(@Valid @RequestBody RegisterCustomerRequest request) {
        CustomerResponse response = customerService.createCustomer(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<VerifyEmailResponse> verifyEmail(@RequestBody VerifyEmailRequest request) {
        VerifyEmailResponse response = customerService.verifyEmail(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<VerifyEmailResponse> forgotPassword(@RequestParam String email) {
        VerifyEmailResponse response = customerService.sendResetOtp(email);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-reset-otp")
    public ResponseEntity<VerifyEmailResponse> verifyResetOtp(@RequestBody VerifyEmailRequest request) {
        VerifyEmailResponse response = customerService.verifyResetOtp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/reset-password")
    public ResponseEntity<VerifyEmailResponse> resetPassword(@RequestParam String email, @RequestParam String newPassword) {
        VerifyEmailResponse response = customerService.resetPassword(email, newPassword);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = customerService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<CustomerResponse> viewProfile(Authentication authentication) {
        String username = authentication.getName(); // lấy token
        CustomerResponse response = customerService.getProfileByUsername(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    public ResponseEntity<CustomerResponse> updateProfile(
            Authentication authentication,
            @Valid @RequestBody UpdateCustomerRequest request) {
        String username = authentication.getName();
        CustomerResponse response = customerService.updateProfile(username, request);
        return ResponseEntity.ok(response);
    }
}
