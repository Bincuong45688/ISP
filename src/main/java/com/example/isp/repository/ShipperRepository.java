package com.example.isp.repository;

import com.example.isp.model.Account;
import com.example.isp.model.Shipper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShipperRepository extends JpaRepository<Shipper, Long> {
    boolean existsByAccount(Account account);
    Optional<Shipper> findByAccount(Account account);
    List<Shipper> findAll();
    Optional<Shipper> findByAccountUsername(String username);
}
