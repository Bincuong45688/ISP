package com.example.isp.repository;

import com.example.isp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<Account> findByUsername(String username);
    Optional<Account> findByEmail(String email);
}
