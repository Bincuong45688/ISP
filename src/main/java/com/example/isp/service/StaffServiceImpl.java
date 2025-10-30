package com.example.isp.service;

import com.example.isp.dto.request.CreateShipperRequest;
import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.RegisterStaffRequest;
import com.example.isp.dto.request.UpdateStaffProfileRequest;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.ShipperResponse;
import com.example.isp.dto.response.StaffResponse;
import com.example.isp.mapper.ShipperMapper;
import com.example.isp.mapper.StaffMapper;
import com.example.isp.model.Account;
import com.example.isp.model.Shipper;
import com.example.isp.model.Staff;
import com.example.isp.model.enums.Role;
import com.example.isp.repository.AccountRepository;
import com.example.isp.repository.ShipperRepository;
import com.example.isp.repository.StaffRepository;
import com.example.isp.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepo;
    private final AccountRepository accountRepo;
    private final ShipperRepository shipperRepo;
    private final ShipperMapper shipperMapper;
    private final StaffMapper staffMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(RegisterStaffRequest req) {
        accountRepo.findByUsername(req.getUsername()).ifPresent(a -> {
            throw new IllegalArgumentException("Username existed");
        });

        Account acc = Account.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .phone(req.getPhone())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.STAFF)
                .build();
        accountRepo.save(acc);

        Staff staff = staffMapper.toEntity(req);
        staff.setAccount(acc);
        staffRepo.save(staff);

        UserDetails principal = User.builder()
                .username(acc.getUsername())
                .password(acc.getPassword())
                .roles(acc.getRole().name())   // dùng .roles => tự thêm ROLE_
                .build();

        String token = jwtService.generateToken(principal);
        return AuthResponse.builder()
                .username(acc.getUsername())
                .email(acc.getEmail())
                .role(acc.getRole().name())
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest req) {
        Account acc = accountRepo.findByUsername(req.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(req.getPassword(), acc.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }
        if (acc.getRole() != Role.STAFF) {
            throw new IllegalStateException("Account is not STAFF");
        }

        UserDetails principal = User.builder()
                .username(acc.getUsername())
                .password(acc.getPassword())
                .roles(acc.getRole().name())
                .build();

        String token = jwtService.generateToken(principal);
        return AuthResponse.builder()
                .token(token)
                .username(acc.getUsername())
                .email(acc.getEmail())
                .role(acc.getRole().name())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public StaffResponse getProfile(String username) {
        Staff staff = staffRepo.findByAccountUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));
        return staffMapper.toResponse(staff);
    }

    @Override
    public StaffResponse updateProfile(String username, UpdateStaffProfileRequest req) {
        Staff staff = staffRepo.findByAccountUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Staff not found"));

        if (req.getStaffName() != null) staff.setStaffName(req.getStaffName());
        Account acc = staff.getAccount();
        if (req.getEmail() != null) acc.setEmail(req.getEmail());
        if (req.getPhone() != null) acc.setPhone(req.getPhone());
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            acc.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        return staffMapper.toResponse(staff);
    }

    @Override
    public void createShipper(CreateShipperRequest req) {
        // 1. Check trùng username
        if(accountRepo.existsByUsername(req.getUsername())) {
            throw  new IllegalArgumentException("Username already existed");
        }

        // 2. Tạo account
        Account account = Account.builder()
                .username(req.getUsername())
                .password(passwordEncoder.encode(req.getPassword()))
                .email(req.getEmail())
                .phone(req.getPhone())
                .role(Role.SHIPPER)
                .status("ACTIVE")
                .build();
        accountRepo.save(account);

        // 3. Tạo thông tin shipper gắn với account
        Shipper shipper = Shipper.builder()
                .shipperName(req.getShipperName())
                .gender(req.getGender())
                .account(account)
                .build();
        shipperRepo.save(shipper);
    }

    @Override
    public List<ShipperResponse> getAllShippers() {
        return shipperRepo.findAll()
                .stream()
                .map(shipperMapper::toResponse)
                .toList();
    }

}
