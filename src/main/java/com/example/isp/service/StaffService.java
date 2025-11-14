package com.example.isp.service;

import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.UpdateShipperProfileRequest;
import com.example.isp.dto.response.*;
import com.example.isp.mapper.StaffMapper;
import com.example.isp.model.Account;
import com.example.isp.model.Shipper;
import com.example.isp.model.Staff;
import com.example.isp.model.enums.Role;
import com.example.isp.repository.AccountRepository;
import com.example.isp.repository.StaffRepository;
import com.example.isp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StaffService {
    private final AccountRepository accountRepository;
    private final StaffRepository staffRepository;
    private final JwtService jwtSerivce;
    private final PasswordEncoder passwordEncoder;
    private final StaffMapper staffMapper;

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }


    // Login
    public AuthResponse login(LoginRequest req) {
        Account acc = accountRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(req.getPassword(), acc.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        if (acc.getRole() != Role.STAFF) {
            throw new IllegalStateException("Account is not SHIPPER");
        }

        UserDetails principal = User.builder()
                .username(acc.getUsername())
                .password(acc.getPassword())
                .roles(acc.getRole().name())
                .build();

        String token = jwtSerivce.generateToken(principal);

        return AuthResponse.builder()
                .token(token)
                .username(acc.getUsername())
                .email(acc.getEmail())
                .role(acc.getRole().name())
                .build();
    }

    // Xem profile
    public StaffProfileResponse getProfile(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));

        Staff staff = staffRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Shipper not found"));

        return StaffProfileResponse.builder()
                .username(account.getUsername())
                .staffName(staff.getStaffName())
                .gender(staff.getGender())
                .phone(account.getPhone())
                .email(account.getEmail())
                .status(account.getStatus())
                .build();
    }

    // Update profile
    @Transactional
    public StaffResponse updateProfile(String username, UpdateStaffProfileRequest req) {
        Staff staff = staffRepository.findByAccountUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Shipper not found"));

        Account acc = staff.getAccount();

        if (req.getStaffName() != null) staff.setStaffName(req.getStaffName());
        if (req.getGender() != null) staff.setGender(req.getGender());
        if (req.getEmail() != null) acc.setEmail(req.getEmail());
        if (req.getPhone() != null) acc.setPhone(req.getPhone());

        return staffMapper.toResponse(staff);
    }
}
