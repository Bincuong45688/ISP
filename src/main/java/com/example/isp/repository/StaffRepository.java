package com.example.isp.repository;

import com.example.isp.model.Account;
import com.example.isp.model.Shipper;
import com.example.isp.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StaffRepository extends JpaRepository<Staff, Long> {
    Optional<Staff> findByAccount(Account account);
    Optional<Staff> findByAccountUsername(String username);
}
