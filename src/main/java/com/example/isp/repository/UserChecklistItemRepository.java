package com.example.isp.repository;

import com.example.isp.model.UserChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserChecklistItemRepository extends JpaRepository<UserChecklistItem, Long> {
    List<UserChecklistItem> findByUserChecklist_Id(Long checklistId);
}
