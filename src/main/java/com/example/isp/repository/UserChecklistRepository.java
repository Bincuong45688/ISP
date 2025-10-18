package com.example.isp.repository;

import com.example.isp.model.UserChecklist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChecklistRepository extends JpaRepository<UserChecklist, Long> {
    List<UserChecklist> findByUserId(Long userId);
}
