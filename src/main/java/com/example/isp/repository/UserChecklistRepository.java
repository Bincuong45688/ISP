package com.example.isp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChecklistRepository extends JpaRepository<UserChecklist, Long> {
    List<UserChecklist> findByUserId(Long userId);
}
