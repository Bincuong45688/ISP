package com.example.isp.service;

import com.example.isp.dto.request.LoginRequest;
import com.example.isp.dto.request.RegisterCustomerRequest;
import com.example.isp.dto.request.UpdateCustomerRequest;
import com.example.isp.dto.request.UpdateProfileRequest;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.CustomerResponse;
import com.example.isp.mapper.CustomerMapper;
import com.example.isp.model.Account;
import com.example.isp.model.Customer;
import com.example.isp.model.enums.Role;
import com.example.isp.repository.AccountRepository;
import com.example.isp.repository.CustomerRepository;
import com.example.isp.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    //Register
    @Override
    public CustomerResponse createCustomer(RegisterCustomerRequest request) {
        // 1. Check trùng username/email
        if(accountRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if(accountRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Tạo Account
        Account account = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(Role.CUSTOMER)
                .build();

        // 3. Tạo customer từ request và gắn vô Acccount
        Customer customer = customerMapper.toEntity(request);
        customer.setAccount(account);

        // 4. Lưu database
        Customer saved = customerRepository.save(customer);

        // 5. Trả về response
        return customerMapper.toResponse(saved);
    }

    //Login
    @Override
    public AuthResponse login(LoginRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));
        if(!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(account.getUsername())
                        .password(account.getPassword())
                        .roles(account.getRole().name())
                        .build()
        );
        return AuthResponse.builder()
                .token(token)
                .username(account.getUsername())
                .email(account.getEmail())
                .role(account.getRole().name())
                .build();
    }

    // Để xem profile
    @Override
    public CustomerResponse getProfileByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Customer customer = customerRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        return customerMapper.toResponse(customer);
    }

    // Cập nhật profile
    @Override
    @Transactional
    public CustomerResponse updateProfile(String username, UpdateProfileRequest request) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        Customer customer = customerRepository.findByAccount(account)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // Cập nhật phần customer
        customerMapper.updateCustomerFromRequest((UpdateCustomerRequest)request, customer);

        // Nếu có email, cập nhật sang account
        Optional.ofNullable(((UpdateCustomerRequest) request).getEmail())
                        .filter(email -> !email.isBlank())
                        .ifPresent(account::setEmail);

        // Nếu có phone, cập nhật sang account
        Optional.ofNullable(((UpdateCustomerRequest) request).getPhoneNumber())
                .filter(phone -> !phone.isBlank())
                .ifPresent(account::setPhone);

        accountRepository.save(account);
        customerRepository.save(customer);

        return customerMapper.toResponse(customer);
    }


}
