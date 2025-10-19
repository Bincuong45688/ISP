package com.example.isp.repository;

import com.example.isp.model.Account;
import com.example.isp.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByAccount(Account account);

    @Query("select c.customerId from Customer c where c.account.username = :username")
    Optional<Long> findIdByAccountUsername(@Param("username") String username);

}
