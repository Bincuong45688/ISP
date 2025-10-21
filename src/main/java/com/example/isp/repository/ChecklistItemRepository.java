package com.example.isp.repository;

import com.example.isp.model.ChecklistItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChecklistItemRepository extends JpaRepository<ChecklistItem, Long>, JpaSpecificationExecutor<ChecklistItem> {

    // Cho phép dùng Specification (lọc theo ritual, checklist)
    @Override
    @EntityGraph(attributePaths = {"ritual", "checklist"})
    Page<ChecklistItem> findAll(Specification<ChecklistItem> spec, Pageable pageable);

    // Lấy tất cả + kèm ritual & checklist
    @EntityGraph(attributePaths = {"ritual", "checklist"})
    @Query("select ci from ChecklistItem ci")
    List<ChecklistItem> findAllWithRelations();

    // Lấy 1 item + kèm ritual & checklist
    @EntityGraph(attributePaths = {"ritual", "checklist"})
    @Query("select ci from ChecklistItem ci where ci.checklistId = :id")
    Optional<ChecklistItem> findByIdWithRelations(@Param("id") Long id);

    // Lấy tất cả items của một ritual
    @EntityGraph(attributePaths = {"ritual", "checklist"})
    @Query("select ci from ChecklistItem ci where ci.ritual.ritualId = :ritualId")
    List<ChecklistItem> findByRitualId(@Param("ritualId") Long ritualId);

    // Lấy tất cả items của một checklist
    @EntityGraph(attributePaths = {"ritual", "checklist"})
    @Query("select ci from ChecklistItem ci where ci.checklist.itemId = :itemId")
    List<ChecklistItem> findByChecklistId(@Param("itemId") Long itemId);
}
