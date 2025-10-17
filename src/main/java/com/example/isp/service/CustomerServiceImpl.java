package com.example.isp.service;

import com.example.isp.dto.request.*;
import com.example.isp.dto.response.AuthResponse;
import com.example.isp.dto.response.CustomerResponse;
import com.example.isp.dto.response.VerifyEmailResponse;
import com.example.isp.mapper.CustomerMapper;
import com.example.isp.model.Account;
import com.example.isp.model.Customer;
import com.example.isp.model.enums.Role;
import com.example.isp.repository.AccountRepository;
import com.example.isp.repository.CustomerRepository;
import com.example.isp.security.JwtService;
import com.example.isp.util.EmailUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final EmailUtil emailUtil;

    //Register
    @Override
    public CustomerResponse createCustomer(RegisterCustomerRequest request) {
        // 1. Kiểm tra trùng email
        Optional<Account> existingAccount = accountRepository.findByEmail(request.getEmail());
        if (existingAccount.isPresent()) {
            Account acc = existingAccount.get();

            // Nếu tài khoản INACTIVE -> gửi lại OTP mới
            if ("INACTIVE".equals(acc.getStatus())) {
                String otp = emailUtil.generateOtp();
                acc.setOtpCode(otp);
                acc.setOtpExpiredAt(LocalDateTime.now().plusMinutes(5));
                accountRepository.save(acc);

                // Gửi lại email xác thực
                emailUtil.sendVerificationEmail(acc.getEmail(), otp);

                throw new RuntimeException("Email đã được đăng ký nhưng chưa xác thực. Hệ thống đã gửi lại mã OTP mới qua email của bạn.");
            }

            // Nếu đã ACTIVE -> không cho đăng ký lại
            throw new RuntimeException("Email already exists");
        }

        // Kiểm tra trùng username
        if(accountRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Tạo Account với trạng thái là INACTIVE
        Account account = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .role(Role.CUSTOMER)
                .status("INACTIVE")
                .build();

        // Tạo và lưu OTP
        String otp = emailUtil.generateOtp();
        account.setOtpCode(otp);
        account.setOtpExpiredAt(LocalDateTime.now().plusMinutes(5));

        // 3. Tạo customer từ request và gắn vô Acccount
        Customer customer = customerMapper.toEntity(request);
        customer.setAccount(account);

        // 4. Lưu database
        Customer saved = customerRepository.save(customer);

        // Gửi email xác thực
        emailUtil.sendVerificationEmail(account.getEmail(), otp);

        // 5. Trả về response
        return customerMapper.toResponse(saved);
    }

    //Verify email
    @Override
    public VerifyEmailResponse verifyEmail(VerifyEmailRequest request) {
        Account account = accountRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Email không tìm thấy"));

        if (account.getOtpExpiredAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Mã OTP đã hết hạn");
        }

        if (!Objects.equals(account.getOtpCode(), request.getOtp())) {
            throw new RuntimeException("Mã OTP không hợp lệ");
        }

        account.setStatus("ACTIVE");
        account.setOtpCode(null);
        account.setOtpExpiredAt(null);
        accountRepository.save(account);

        return VerifyEmailResponse.builder()
                .message("Xác thực email thành công!")
                .status("ACTIVE")
                .build();
    }

    //Login
    @Override
    public AuthResponse login(LoginRequest request) {
        // 1. Tìm account theo username
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Username not found"));

        // 2. Kiểm tra trạng thái xác thực qua email
        if("INACTIVE".equals(account.getStatus())) {
            throw new RuntimeException("Tài khoản chưa xác thực email. Vui lòng kiểm tra hộp thư để kích hoạt tài khoản.");
        }

        // 3. Kiểm tra password
        if(!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // 4. Tạo token
        String token = jwtService.generateToken(
                org.springframework.security.core.userdetails.User
                        .withUsername(account.getUsername())
                        .password(account.getPassword())
                        .roles(account.getRole().name())
                        .build()
        );

        // 5. Trả về response
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
