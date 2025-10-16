package com.example.isp.repository;

import com.example.isp.model.enums.Checklist;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChecklistRepository extends JpaRepository<Checklist, Long> {
    List<Checklist> findByRitualId(Long ritualId);
}
