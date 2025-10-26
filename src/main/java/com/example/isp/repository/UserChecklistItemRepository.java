package com.example.isp.repository;

import com.example.isp.model.UserChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChecklistItemRepository extends JpaRepository<UserChecklistItem, Long> {

    // Find all items by user checklist
    List<UserChecklistItem> findByUserChecklist_UserChecklistId(Long userChecklistId);

    // Delete all items by user checklist
    void deleteByUserChecklist_UserChecklistId(Long userChecklistId);
}
