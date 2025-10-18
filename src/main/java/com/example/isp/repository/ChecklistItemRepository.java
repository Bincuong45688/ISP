package com.example.isp.repository;

import com.example.isp.model.ChecklistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long> {
    List<ChecklistItem> findByItemNameContainingIgnoreCase(String keyword);
}

