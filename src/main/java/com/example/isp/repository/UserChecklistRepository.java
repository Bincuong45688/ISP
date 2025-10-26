package com.example.isp.repository;

import com.example.isp.model.UserChecklist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserChecklistRepository extends JpaRepository<UserChecklist, Long> {

    // Find all checklists by user
    Page<UserChecklist> findByUser_CustomerId(Long userId, Pageable pageable);

    // Find by user and ritual
    Page<UserChecklist> findByUser_CustomerIdAndRitual_RitualId(Long userId, Long ritualId, Pageable pageable);

    // Find by user and title containing
    Page<UserChecklist> findByUser_CustomerIdAndTitleContainingIgnoreCase(Long userId, String title, Pageable pageable);

    // Find checklists that need notification
    @Query("SELECT uc FROM UserChecklist uc WHERE uc.isNotified = false AND uc.reminderDate <= :now")
    List<UserChecklist> findChecklistsNeedingNotification(@Param("now") LocalDateTime now);

    // Find by user with filters
    @Query("SELECT uc FROM UserChecklist uc WHERE uc.user.customerId = :userId " +
           "AND (:ritualId IS NULL OR uc.ritual.ritualId = :ritualId) " +
           "AND (:title IS NULL OR LOWER(uc.title) LIKE LOWER(CONCAT('%', :title, '%')))")
    Page<UserChecklist> findByUserWithFilters(
        @Param("userId") Long userId,
        @Param("ritualId") Long ritualId,
        @Param("title") String title,
        Pageable pageable
    );
}
