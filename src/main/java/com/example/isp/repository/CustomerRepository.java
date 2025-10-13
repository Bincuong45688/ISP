package com.example.isp.repository;

import com.example.isp.model.Account;
import com.example.isp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByAccount(Account account);
}
