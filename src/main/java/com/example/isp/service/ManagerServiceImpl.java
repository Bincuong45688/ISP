package com.example.isp.service;

import com.example.isp.dto.request.CreateShipperRequest;
import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.CreateStaffRequest;
import com.example.isp.dto.request.UpdateManagerProfileRequest;
import com.example.isp.dto.response.*;
import com.example.isp.mapper.CustomerMapper;
import com.example.isp.mapper.ManagerMapper;
import com.example.isp.mapper.ShipperMapper;
import com.example.isp.mapper.StaffMapper;
import com.example.isp.model.Account;
import com.example.isp.model.Manager;
import com.example.isp.model.Shipper;
import com.example.isp.model.Staff;
import com.example.isp.model.enums.Role;
import com.example.isp.repository.*;
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
public class ManagerServiceImpl implements ManagerService {

    private final ManagerRepository manaRepo;
    private final AccountRepository accountRepo;
    private final CustomerRepository customerRepo;
    private final ShipperRepository shipperRepo;
    private final StaffRepository staffRepo;
    private final ShipperMapper shipperMapper;
    private final StaffMapper staffMapper;
    private final CustomerMapper customerMapper;
    private final ManagerMapper managerMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(CreateStaffRequest req) {
        accountRepo.findByUsername(req.getUsername()).ifPresent(a -> {
            throw new IllegalArgumentException("Username existed");
        });

        Account acc = Account.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .phone(req.getPhone())
                .password(passwordEncoder.encode(req.getPassword()))
                .role(Role.MANAGER)
                .build();
        accountRepo.save(acc);

        Manager manager = managerMapper.toEntity(req);
        manager.setAccount(acc);
        manaRepo.save(manager);

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
        if (acc.getRole() != Role.MANAGER) {
            throw new IllegalStateException("Account is not MANAGER");
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
    public ManagerResponse getProfile(String username) {
        Manager manager = manaRepo.findByAccountUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
        return managerMapper.toResponse(manager);
    }

    @Override
    public ManagerResponse updateProfile(String username, UpdateManagerProfileRequest req) {
        Manager manager = manaRepo.findByAccountUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Mana not found"));

        if (req.getManagerName() != null) manager.setManagerName(req.getManagerName());
        Account acc = manager.getAccount();
        if (req.getEmail() != null) acc.setEmail(req.getEmail());
        if (req.getPhone() != null) acc.setPhone(req.getPhone());
        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            acc.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        return managerMapper.toResponse(manager);
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
    public void createStaff(CreateStaffRequest req) {
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
                .role(Role.STAFF)
                .status("ACTIVE")
                .build();
        accountRepo.save(account);

        // 3. Tạo thông tin staff gắn với account
        Staff staff = Staff.builder()
                .staffName(req.getStaffName())
                .gender(req.getGender())
                .account(account)
                .build();
        staffRepo.save(staff);
    }

    @Override
    public List<ShipperResponse> getAllShippers() {
        return shipperRepo.findAll()
                .stream()
                .map(shipperMapper::toResponse)
                .toList();
    }

    @Override
    public List<StaffResponse> getAllStaff() {
        return staffRepo.findAll()
                .stream()
                .map(staffMapper::toResponse)
                .toList();
    }

    @Override
    public List<AllCustomerResponse> getAllCustomer() {
        return customerRepo.findAll()
                .stream()
                .map(customerMapper::toGetAllResponse)
                .toList();
    }


}
