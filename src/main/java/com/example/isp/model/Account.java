package com.example.isp.model;

import com.example.isp.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity @Table(name = "account")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Account {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    private String status;

    @Column(name = "otp_code")
    private String otpCode;

    @Column(name = "otp_expired_at")
    private LocalDateTime otpExpiredAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")  // cột khóa ngoại trong bảng account
    private Customer customer;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Ho_Chi_Minh")
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
